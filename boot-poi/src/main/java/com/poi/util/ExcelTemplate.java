package com.poi.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建Excel模版类,用于来加载模版Excel表
 */
public class ExcelTemplate {
    // 创建模版标示符
    private static final String DATA_LINE = "datas";
    //excel样式标示符
    private static final String DEFAULT_STYLE="defaultStyles";
    private static final String STYLE="styles";

    //样式容器、用于装载第几列所在的样式
    private Map<Integer, CellStyle> styles;

    // 声明Wrokbook
    private Workbook workbook;
    //声明sheet表格
    private Sheet sheet;
    //声明row
    private Row currRow;
    //声明最后一行数据
    private int lastRowIndex;
    //初始化行的下标
    private int initRowIndex;
    //初始化列的下标
    private int initCollIndex;
    //当前行的下标
    private int currRowIndex;
    //当前列的下标
    private int currCollIndex;

    //excel默认样式
    private CellStyle defaultStyle;
    //设置默认行高样式
    private float rowHeight;
    //用于记录sernums序列号列列的坐标
    private int colSernums;
    // single模式
    private static ExcelTemplate excelTemplate = new ExcelTemplate();

    private ExcelTemplate() {
    }

    public static ExcelTemplate getInstance() {
        return excelTemplate;
    }

    /** 第一步、读取相应模版文档,该方法通过classpath方式读取模版*/
    public void readExcelTemplateByClassPath(String path) {
        try {
            workbook = WorkbookFactory.create(ExcelTemplate.class.getResourceAsStream(path));
            //初始化模版
            initTemplate();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("Excel模版格式不正确!");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Excel模版不存在!");
        }
    }
    /**第二种读取模版方式、通过path来继续读取*/
    public void readExcelTemplateByPath(String path) {
        try {
            workbook = WorkbookFactory.create(new File(path));
            //初始化模版
            initTemplate();
            //创建行
            createNewRow();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("Excel模版格式不正确!");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Excel模版不存在!");
        }
    }

    //初始化模版
    private void initTemplate(){
        //得到模版中第一个sheet表格
        sheet=workbook.getSheetAt(0);
        //初始化配置信息
        initConfigData();
        //获取最后一行的数据坐标
        lastRowIndex=sheet.getLastRowNum();
        //初始化完成,创建一行
        //currRow=sheet.getRow(currRowIndex);
        createNewRow();
    }

    //初始化模版中的数据信息找到标识符的坐标
    private void initConfigData() {
        boolean isFind=false;
        boolean isFindSernums=false;
        for (Row row : sheet) {
            //如果找到对应的数据就结束
            if(isFind) break;

            for (Cell cell : row) {
                //如果当前行的数据类型不是字符串类型就继续,因为我们在excel模版中设置的是datas是字符串类型
                if(cell.getCellType()!=Cell.CELL_TYPE_STRING) continue;
                //获取到当前单元格的值
                String cellValue=cell.getStringCellValue().trim();
                //找序列号的标示符sernums
                if(cellValue.equals("sernums")){
                    colSernums=cell.getColumnIndex();
                    isFindSernums=true;
                }
                if(cellValue.equals("datas")){
                    //获取当前单元格行的坐标
                    initRowIndex=cell.getRowIndex();
                    //获取当前单元格列的坐标
                    initCollIndex=cell.getColumnIndex();
                    currRowIndex=initRowIndex;
                    currCollIndex=initCollIndex;
                    //获取默认行高
                    rowHeight=row.getHeightInPoints();
                    //初始化模版样式
                    initStyles();
                    isFind=true;
                    break;
                }

            }
        }
        //最后检查一下在初始化数据的时候有没有找到，如果没找到就初始化序列号
        initSernums();
    }

    //将模版样式初始化数据加载到styles容器中
    private void initStyles() {

        //初始化样式容器
        styles= new HashMap<Integer, CellStyle>();
        for (Row row : sheet) {
            for (Cell cell : row) {
                if(cell.getCellType()!=Cell.CELL_TYPE_STRING) continue;
                String cellValue=cell.getStringCellValue().trim();
                //如果当前列的值等于defaultstyles设置成默认样式
                if(DEFAULT_STYLE.equals(cellValue)){
                    defaultStyle=cell.getCellStyle();
                }
                if(STYLE.equals(cellValue)){
                    styles.put(cell.getColumnIndex(), cell.getCellStyle());
                }
            }
        }
    }

    //初始化模版sernums序列号的坐标
    private void initSernums() {
        for (Row row : sheet) {
            for (Cell cell : row) {
                //如果当前行的数据类型不是字符串类型就继续,因为我们在excel模版中设置的是sernums是字符串类型
                if(cell.getCellType()!=Cell.CELL_TYPE_STRING) continue;
                //获取到当前单元格的值
                String cellValue=cell.getStringCellValue().trim();
                if(cellValue.equals("sernums")){
                    //获取到序列号sernums的坐标
                    colSernums=cell.getColumnIndex();
                }
            }
        }
    }


    //用于替换一些常量固定的值
    public void replaceConstant(Map<String, String> datas){
        //数据常量如果为空就不进行替换
        if(null==datas) return;

        for (Row row : sheet) {
            for (Cell cell : row) {
                String cellValue=cell.getStringCellValue().trim();
                //如果当前模版中式#开头就是我们所需要替换的常量
                if(cellValue.startsWith("#")){
                    //如果datas数据中包含有我们的值就进行常量设置
                    String key=cellValue.substring(1);
                    if(datas.containsKey(key)){
                        cell.setCellValue(datas.get(key));
                    }
                }
            }
        }
    }

    //创建行
    public void createNewRow() {
        if(lastRowIndex>currRowIndex && currRowIndex!=initRowIndex){
            //移动下一行
            sheet.shiftRows(currRowIndex, lastRowIndex, 1, true, true);
            lastRowIndex++;
        }

        currRow=sheet.createRow(currRowIndex);
        //设置行高
        currRow.setHeightInPoints(rowHeight);
        //下一行
        currRowIndex++;
        //当创建到下一行的单元格的列就应该是初始化的列的坐标
        currCollIndex=initCollIndex;
    }

    //创建列,并为当前单元格赋值
    public void createCell(String cellValue){
        Cell cell=currRow.createCell(currCollIndex);
        cell.setCellValue(cellValue);
        setCellStyle(cell);
        //到下一个单元格
        currCollIndex++;
    }
    //插入序列号
    public void insertSernums(){
        int index=1;
        Row row=null;
        Cell cell= null;
        for (int i = initRowIndex; i < currRowIndex; i++) {
            //得到当前行
            row=sheet.getRow(i);
            cell=row.createCell(colSernums);
            setCellStyle(cell);
            cell.setCellValue(index++);
        }
    }

    //设置样式
    public void setCellStyle(Cell cell){
        //设置的时候将样式添加进去
        cell.setCellStyle(styles.containsKey(currCollIndex)?styles.get(currCollIndex):defaultStyle);
    }

    //将数据到处到Excel中
    public void writeFilePath(String filePath){
        FileOutputStream fos=null;
        try {
            fos= new FileOutputStream(new File(filePath));
            workbook.write(fos);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("写入的文件不存在!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("写入流失败!");
        }finally{
            try {
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void writeStream(OutputStream os){
        try {
            workbook.write(os);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("写入流失败!");
        }finally{
            try {
                os.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
