package com.limu.file;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public class ExportCsv {

    public static String exportCsv(List<?> dataList){
        if(dataList.isEmpty()){
            return null;
        }
        //存放地址&文件名
        String fileName = "D:\\Java学习\\colleges_export\\"+ MyCsvFileUtil.buildCsvFileFileName(dataList);
        //创建表格行标题
        String tableNames = ExcelUtils.buildCsvFileTableNamesNew(ExcelUtils.resolveExcelTableName(dataList.get(0)));
        //创建文件
        MyCsvFileUtil.writeFile(fileName, tableNames);
        //写入数据
        String contentBody = MyCsvFileUtil.buildCsvFileBodyMap(dataList);
        //调用方法生成
        MyCsvFileUtil.writeFile(fileName, contentBody);

        return fileName;
    }

}
