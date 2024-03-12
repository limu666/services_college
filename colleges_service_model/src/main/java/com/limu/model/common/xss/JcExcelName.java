package com.limu.model.common.xss;

/*
* 自定义导出Excel数据注解
*
* @author: 李牧
* */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JcExcelName {

    String name() default "";

}
