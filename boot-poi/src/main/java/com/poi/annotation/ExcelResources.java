package com.poi.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 通过新加class类一样,在选择新家的时候
 * 选择新建@Annotation 即可
 * 当新建的Annotation之后需要进行策略注解
 *
 * @Retention(RetentionPolicy.RUNTIME)
 * @Retention指明其策略 RetentionPolicy.RUNTIME 指明其策略是 运行时策略
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelResources {
    //声明策略的值
    String title();

    //声明一个order值,并且其默认值为9999
    int order() default 9999;
}
