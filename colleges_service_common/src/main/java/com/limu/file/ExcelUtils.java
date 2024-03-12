package com.limu.file;


import com.limu.model.common.xss.JcExcelName;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 根据注解来命名字段名
* @author: 李牧
* */
public class ExcelUtils {

    // 反射解析拿字段属性注解值函数
    public static <T> List<String> resolveExcelTableName(T entity) {

        List<String> tableNamesList = new ArrayList<>();
        Class<?> bean = entity.getClass();
        Field[] fields = bean.getDeclaredFields();
        Map<String, Object> map = new HashMap<>(fields.length);
        for (Field field : fields) {
            try {
                if (!"serialVersionUID".equals(field.getName())) {
                    String tableTitleName = field.getName();
                    JcExcelName myFieldAnn = field.getAnnotation(JcExcelName.class);
                    if(myFieldAnn.name() == null){
                        tableTitleName = null;
                    }else{
                        String annName = myFieldAnn.name();
                        tableTitleName = annName;
                    }
                    tableNamesList.add(tableTitleName);
                }
            } catch (Exception e) {
                System.out.println("toMap() Exception: " + e.getMessage());
            }
        }
        return tableNamesList;
    }

    // 根据解析出来的注解值列名拼接 表格标题名格式
    public static String buildCsvFileTableNamesNew(List<String> dataList) {
        StringBuilder tableNames = new StringBuilder();
        for (String name : dataList) {
            tableNames.append(name).append(MyCsvFileUtil.CSV_DELIMITER);
        }
        return tableNames.append(MyCsvFileUtil.CSV_TAIL).toString();
    }




}