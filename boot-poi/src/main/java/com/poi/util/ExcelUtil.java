package com.poi.util;

import com.poi.annotation.ExcelResources;
import com.poi.model.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 导出EXCEL工具类
 */
public class ExcelUtil {
    private static ExcelUtil excelUtil = new ExcelUtil();

    private ExcelUtil() {
    }

    ;

    public static ExcelUtil getInstance() {
        return excelUtil;
    }

    /**
     * 通过excel模版的方式,将数据对象导入到Excel中
     *
     * @param template                                  :excel的模板
     * @param outPath:文件输出路径                            输出到那里
     * @param objs:数据列表、用于要输出的数据对象
     * @param constantMap:用于输出一些特殊的一些常量                 如#tiltle=标题
     * @param clz:导入那个,其通过反射机制实现
     * @param isclassPath:是否通过classpath的加载方式进行加载excel模版
     */
    @SuppressWarnings("rawtypes")
    public void exportObjToExcelByTemplate(String template, String outPath, List objs, Map constantMap, Class clz, boolean isclassPath) {
        ExcelTemplate excelTemplate = HanderExcelByTemplate(template, objs, constantMap, clz, isclassPath);
        excelTemplate.writeFilePath(outPath);

    }
    /**
     * 通过excel模版的方式,将数据对象导入到Excel中
     *
     * @param template                                  :excel的模板
     * @param os:通过输出流
     * @param objs:数据列表、用于要输出的数据对象
     * @param constantMap:用于输出一些特殊的一些常量                 如#tiltle=标题
     * @param clz:导入那个,其通过反射机制实现
     * @param isclassPath:是否通过classpath的加载方式进行加载excel模版
     */
    @SuppressWarnings("rawtypes")
    public void exportObjToExcelByTemplate(String template, OutputStream os, List objs, Map constantMap, Class clz, boolean isclassPath) {
        ExcelTemplate excelTemplate = HanderExcelByTemplate(template, objs, constantMap, clz, isclassPath);
        excelTemplate.writeStream(os);

    }

    /**
     * 不通过excel模版的方式,直接将数据对象导入到Excel中
     *
     * @param outPath:文件输出路径                        输出到那里
     * @param objs:数据列表、用于要输出的数据对象
     * @param clz:导入那个,其通过反射机制实现
     * @param isXssF:true=2007excel,false=2003excel
     */
    @SuppressWarnings("rawtypes")
    public void exportObjToExcel(String outPath, List objs, Class clz, boolean isXssF) {

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(outPath));
            Workbook workbook = HanderExcel(objs, clz, isXssF);
            workbook.write(fos);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    /**
     * 不通过excel模版的方式,直接将数据对象导入到Excel中
     *
     * @param os:输出流
     * @param objs:数据列表、用于要输出的数据对象
     * @param clz:导入那个,其通过反射机制实现
     * @param isXssF:true=2007excel,false=2003excel
     */
    @SuppressWarnings("rawtypes")
    public void exportObjToExcel(OutputStream os, List objs, Class clz, boolean isXssF) {
        try {
            Workbook workbook = HanderExcel(objs, clz, isXssF);
            workbook.write(os);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 通过excel模版的方式,来处理excel,该方法需要提供Excel的模版,并进行在excel中添加模版标识符
     *
     * @param template                                  :excel的模板
     * @param objs:数据列表、用于要输出的数据对象
     * @param constantMap:用于输出一些特殊的一些常量                 如#tiltle=标题
     * @param clz:导入那个,其通过反射机制实现
     * @param isclassPath:是否通过classpath的加载方式进行加载excel模版
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private ExcelTemplate HanderExcelByTemplate(String template, List objs, Map constantMap, Class clz, boolean isclassPath) {
        //读取模版
        ExcelTemplate excelTemplate = ExcelTemplate.getInstance();
        if (isclassPath) {
            //通过classpath方式读取模版
            excelTemplate.readExcelTemplateByClassPath(template);
        } else {
            //通过path方式读取模版
            excelTemplate.readExcelTemplateByPath(template);
        }
        List<ExcelHeader> headers = getExcelHeader(clz);
        //对list进行排序
        Collections.sort(headers);

        //将其标题项写入到excel中：用户标识、用户名称、用户昵称、用户年龄
        //创建一行
        excelTemplate.createNewRow();
        //将标题插入到当前行中
        for (ExcelHeader excelHeader : headers) {
            excelTemplate.createCell(excelHeader.getTilte());
        }
        //开始写入对应数据,ExcelHeader是排序的,因此根据ExcelHeader进行插入
        //这里插入是通过反射机制进行
        if (null != objs && objs.size() > 0) {
            for (Object obj : objs) {
                //首先创建一行
                excelTemplate.createNewRow();
                for (ExcelHeader excelHeader : headers) {
                    try {
                        //通过之前保存在excelHeader中的get方法得到相对应的方法
                        Method method = clz.getMethod(excelHeader.getMethodName());
                        //通过反射得到对象对应的值
                        Object returnValue = method.invoke(obj);
                        excelTemplate.createCell(String.valueOf(returnValue));

                    } catch (NoSuchMethodException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        //用于替换一些特殊常量字符
        if (null != constantMap && constantMap.size() > 0)
            excelTemplate.replaceConstant(constantMap);

        return excelTemplate;

    }

    /**
     * @param objs 要写入的对象集合
     * @param clz  写入的对象
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private Workbook HanderExcel(List objs, Class clz, boolean isXssF) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Workbook workbook = null;
        if (isXssF) {
            //创建2007excel后缀是.xlsx
            workbook = new XSSFWorkbook();
        } else {
            //创建2003excel后缀是.xls
            workbook = new HSSFWorkbook();
        }

        //创建sheet
        Sheet sheet = workbook.createSheet();
        //得到第一行,在excel中第一行第一列的坐标是(0,0)
        Row row = sheet.createRow(0);
        //获取传入数据的标题,其就是annotation
        List<ExcelHeader> excelHeaders = getExcelHeader(clz);
        //对头部数据进行排序
        Collections.sort(excelHeaders);
        //将表头插入到第一行
        for (int i = 0; i < excelHeaders.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(excelHeaders.get(i).getTilte());
        }
        //数据不为空的时候执行
        if (null != objs && objs.size() > 0) {

            /**
             * 设置时间类型样式，该样式创建不能放在循环中，否则会报异常
             * java.lang.IllegalStateException: The maximum number of cell styles was exceeded. You can define up to 4000 styles in a .xls workbook
             */
            CellStyle cellStyle = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd HH:mm:ss"));
            //插入对象数据
            for (int i = 0; i < objs.size(); i++) {
                Object obj = objs.get(i);
                //新建一行
                Row dataRow = sheet.createRow(i + 1);
                for (int j = 0; j < excelHeaders.size(); j++) {
                    ExcelHeader e = excelHeaders.get(j);
                    //得到对应的get方法
                    Method method = clz.getMethod(e.getMethodName());
                    try {
                        Object objectName = method.getReturnType().getName();
                        String value = String.valueOf(method.invoke(obj));
                        //将对应的值插入进去
                        if (objectName.equals("java.sql.Timestamp") || objectName.equals("java.sql.Date") || objectName.equals("java.util.Date")) {
                            Cell cell = dataRow.createCell(j);
                            if (value == null || value.equals("null")) {
                                cell.setCellValue("");
                            } else {
                                Object dateValue = method.invoke(obj);
                                if (objectName.equals("java.sql.Timestamp"))
                                    cell.setCellValue((java.sql.Timestamp) dateValue);
                                else if (objectName.equals("java.sql.Date"))
                                    cell.setCellValue((java.sql.Date) dateValue);
                                else if (objectName.equals("java.util.Date"))
                                    cell.setCellValue((java.util.Date) dateValue);
                                cell.setCellStyle(cellStyle);
                            }
                        } else {
                            dataRow.createCell(j).setCellValue(value == null || value.equals("null") ? "" : value);
                        }

                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }

            }
        }
        return workbook;
    }


    /**
     * 通过反射机制,得到在User类中的注解
     */
    @SuppressWarnings("rawtypes")
    public List<ExcelHeader> getExcelHeader(Class clz) {
        List<ExcelHeader> headers = new ArrayList<ExcelHeader>();
        Method[] methods = clz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            //获取当前方法
            Method method = methods[i];
            //获取方法名称、我们所有的注解都是注解在get方法的,因此只需要获取get有关的方法
            String methodName = method.getName();
            if (methodName.startsWith("get")) {
                //获取到get方法中对应的注解
                ExcelResources er = method.getAnnotation(ExcelResources.class);
                //当一个类中存在有些字段的get未添加annotation 就不进行添加
                if (er != null)
                    headers.add(new ExcelHeader(er.title(), er.order(), methodName));
            }
        }

        return headers;
    }


//-------------------------以上是写入excel的方法、以下是将数据从excel中读出来----------


    @SuppressWarnings("rawtypes")
    public List<Object> readExcelToObjectByClassPath(String classPath, Class clz, int readLine, int tailLine) {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(ExcelUtil.class.getResourceAsStream(classPath));
            return handlerExcelToObject(workbook, clz, readLine, tailLine);
        } catch (InvalidFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    public List<Object> readExcelToObjectByPath(String path, Class clz, int readLine, int tailLine) {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(new File(path));
            return handlerExcelToObject(workbook, clz, readLine, tailLine);
        } catch (InvalidFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    @SuppressWarnings("rawtypes")
    public List<Object> readExcelToObjectByClassPath(String classPath, Class clz) {
        return readExcelToObjectByClassPath(classPath, clz, 0, 0);
    }

    /***
     * 该方法默认从0行开始读取数据
     * @param path
     * @param clz
     */
    @SuppressWarnings("rawtypes")
    public List<Object> readExcelToObjectByPath(String path, Class clz) {
        return readExcelToObjectByPath(path, clz, 0, 0);
    }


    /**
     * desc：该方法用于处理excel、读取excel中的数据并将转换成clz对应的类
     *
     * @param workbook
     * @param clz
     * @param readLine 从第几行开始读取
     * @param tailLine 尾部有几行不需要读取
     */

    @SuppressWarnings("rawtypes")
    private List<Object> handlerExcelToObject(Workbook workbook, Class clz, int readLine, int tailLine) {
        List<Object> objects = new ArrayList<Object>();

        try {
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(readLine);
            Map<Integer, String> maps = getExcelHeaderMap(row, clz);
            /***
             * i = readLine+1说明、第一行读取的时标题行、因此读取数据应从标题的下一行开始 readLine+1
             * sheet.getLastRowNum()-tailLine 说明：总共需要读取的行数-不需要读取的行数=需要读取的行数
             */
            for (int i = readLine + 1; i < sheet.getLastRowNum() - tailLine; i++) {
                //实例化对应的对象
                Object obj = clz.newInstance();
                //得到当前行
                Row currRow = sheet.getRow(i);
                //循环当前行所有列的数据
                for (Cell cell : currRow) {
                    //当前列的坐标
                    int currColIndex = cell.getColumnIndex();
                    //得到当前列对应的方法名称
                    String methodName = maps.get(currColIndex);

                    /**
                     * 此处用java反射机制进行方法的反射，由于如果对象有int，date之类的参数，在这里反射就不是特别好用
                     * 因此这里可以在maven配置
                     *
                     * <dependency>
                     <groupId>commons-beanutils</groupId>
                     <artifactId>commons-beanutils</artifactId>
                     <version>1.9.2</version>
                     </dependency>
                     beanutils的依赖用beantils来进行
                     * */
                    //注意这里如果方法的参数是Integer或者Date或者其他类型需要做判定，而我这里属性全部都定义成String类型的
                    //  Method method=clz.getMethod(methodName, String.class);
                    //通过反射将单元格对应的值、调用对象的setXXX方法实现对数据的添加
                    //method.invoke(obj, cell.getStringCellValue());

                    /**
                     *  BeanUtils.copyProperty(obj, methodName, method);
                     *  说明 obj对象
                     *  methodName方法 在beantuils这里只要提供属性名称即可 若 方法名setUserName这里只需要时username
                     *
                     * */
                    methodName = methodName.substring(3);
                    methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
                    BeanUtils.copyProperty(obj, methodName, cell.getStringCellValue());

                }
                //将添加到集合中去
                objects.add(obj);
            }
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return objects;
    }


    /***
     *
     * @param titleRow 标题行
     * @param clz
     * @return Map<Integer, String> 对应列所对应的方法
     */
    @SuppressWarnings("rawtypes")
    private Map<Integer, String> getExcelHeaderMap(Row titleRow, Class clz) {
        //首先获取类上上面所注解的标题
        List<ExcelHeader> headers = getExcelHeader(clz);
        //用于存储某一列所对应的方法的值
        Map<Integer, String> maps = new HashMap<Integer, String>();
        //根据header列表找到对应的数据列表
        for (ExcelHeader excelHeader : headers) {
            for (Cell cell : titleRow) {
                if (cell.getStringCellValue().trim().equals(excelHeader.getTilte())) {
                    //找到当前列所对应的值将存储起来,而这里我们是得到get方法，需要获取set方法，为对象设置
                    maps.put(cell.getColumnIndex(), excelHeader.getMethodName().replace("get", "set"));
                    break;
                }
            }
        }
        return maps;
    }

    //用于测试
    public static void main(String[] args) {
        List<User> users = new ArrayList<User>();
//  users.add( new User(1+"", "admin", "用户管理员", 12+""));
//  users.add( new User(2+"", "mexcian", "稻草人", 13+""));
//  users.add( new User(3+"", "jacky", "杰克", 14+""));
//  users.add( new User(4+"", "ksea", "海洋", 15+""));
//  users.add( new User(5+"", "a", "a5", 16+""));
//  users.add( new User(6+"", "b", "b6", 17+""));
//  users.add( new User(7+"", "c", "c7", 18+""));
//  users.add( new User(8+"", "d", "d8", 19+""));
//  users.add( new User(9+"", "e", "e9", 20+""));
//  users.add( new User(10+"", "f", "10", 21+""));
//  users.add( new User(11+"", "g", "g11", 22+""));


        users.add(new User(1, "admin", "用户管理员", 12));
        users.add(new User(2, "mexcian", "稻草人", 13));
        users.add(new User(3, "jacky", "杰克", 14));
        users.add(new User(4, "ksea", "海洋", 15));
        users.add(new User(5, "a", "a5", 16));
        users.add(new User(6, "b", "b6", 17));
        users.add(new User(7, "c", "c7", 18));
        users.add(new User(8, "d", "d8", 19));
        users.add(new User(9, "e", "e9", 20));
        users.add(new User(10, "f", "10", 21));
        users.add(new User(11, "g", "g11", 22));
        //-------------------用于测试通过模版加载--------------------------------------
        //Map<String, String> maps= new HashMap<String, String>();
        //maps.put("title", "用户信息手册");
        //ExcelUtil.getInstance().exportObjToExcelByTemplate("/excel/template/user.xls","e:/poi/tuser.xls", users,maps, User.class, true);


        //用于测试通过不用模版加载
        //excelUtil.getInstance().exportObjToExcel("e:/poi/t_user.xlsx", users, User.class, true);


        //读取excel测试，通过指定的第几行开始
        //List<Object> objs=excelUtil.getInstance().readExcelToObjectByPath("E:/poi/tuser.xls", User.class,2,2);
        //默认从0行开始
        List<Object> objs = excelUtil.getInstance().readExcelToObjectByPath("E:/poi/t_user.xlsx", User.class);
        for (Object object : objs) {
            User u = (User) object;
            System.out.println(u);
        }
        //excelUtil.getInstance().readExcelToObjectByPath("E:/poi/t_user.xlsx", User.class, 0);
    }
}
