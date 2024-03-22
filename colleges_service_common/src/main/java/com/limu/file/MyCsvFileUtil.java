package com.limu.file;

import com.limu.model.common.dtos.ResponseResult;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 根据字段返回列名称
 * @author limu
 * @Remark 是我
 */
//@Slf4j
public class MyCsvFileUtil {
    public static final String FILE_SUFFIX = ".csv";
    public static final String CSV_DELIMITER = ",";
    public static final String CSV_TAIL = "\r\n";
    protected static final String DATE_STR_FILE_NAME = "yyyyMMddHHmmssSSS";

    /**
     * 将字符串转成csv文件
     */
    public static void createCsvFile(String savePath, String contextStr) throws IOException {

        File file = new File(savePath);
        //创建文件
        file.createNewFile();
        //创建文件输出流
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        //将指定字节写入此文件输出流
        fileOutputStream.write(contextStr.getBytes(StandardCharsets.UTF_8));
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    /**
     * 写文件
     *
     * @param fileName
     * @param content
     */
    public static void writeFile(String fileName, String content) {
        FileOutputStream fos = null;
        OutputStreamWriter writer = null;
        try {
            fos = new FileOutputStream(fileName, true);
            writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer.write(content);
            writer.flush();
        } catch (Exception e) {
//            log.error("写文件异常|{}", e);
            System.out.println("写文件异常|{}");
        } finally {
            if (fos != null) {
                IOUtils.closeQuietly(fos);
            }
            if (writer != null) {
                IOUtils.closeQuietly(writer);
            }
        }
    }

    /**
     * 构建文件名称
     * @param dataList
     * @return String
     */
    public static String buildCsvFileFileName(List<?> dataList) {
        return dataList.get(0).getClass().getSimpleName() + new SimpleDateFormat(DATE_STR_FILE_NAME).format(new Date()) + FILE_SUFFIX;
    }

    /**
     * 构建excel 标题行名
     * @param dataList<?>
     * @return String
     */
    public static String buildCsvFileTableNames(List<?> dataList) {
        Map<String, Object> map = toMap(dataList.get(0));
        StringBuilder tableNames = new StringBuilder();
        for (String key : map.keySet()) {
            tableNames.append(key).append(MyCsvFileUtil.CSV_DELIMITER);
        }
        return tableNames.append(MyCsvFileUtil.CSV_TAIL).toString();
    }

    /**
     * 构建excel内容
     * @param dataLists
     * @return
     */
    public static String buildCsvFileBodyMap(List dataLists) {

        // 不管什么玩意，都给反射一手，搞成 LinkedHashMap,进行有序排列
        List<LinkedHashMap<String, Object>> mapList = new ArrayList<>();
        for (Object o : dataLists) {
            mapList.add(toLinkedHashMap(o));
        }
        // 然后利用csv格式，对着map嘎嘎一顿拼接数据
        StringBuilder lineBuilder = new StringBuilder();
        for (LinkedHashMap<String, Object> rowData : mapList) {
            for (Map.Entry<String, Object> entry : rowData.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                // 此处进行字符串判断，防止有\n换行字符出现，判断完毕后重新赋值
                if (value instanceof String) {
                    String strValue = (String) value;
                    String escapedValue  = strValue.replaceAll("\n", "\\n");
                    value = escapedValue;
                }
                if (Objects.nonNull(value)) {
                    lineBuilder.append(value).append(MyCsvFileUtil.CSV_DELIMITER);
                } else {
                    lineBuilder.append("--").append(MyCsvFileUtil.CSV_DELIMITER);
                }
            }
            lineBuilder.append(MyCsvFileUtil.CSV_TAIL);
        }
        return lineBuilder.toString();
    }

    public static <T> LinkedHashMap<String, Object> toLinkedHashMap(T entity) {
        Class<? extends Object> bean = entity.getClass();
        Field[] fields = bean.getDeclaredFields();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>(fields.length);
        for (Field field : fields) {
            try {
                if (!"serialVersionUID".equals(field.getName())) {
                    String methodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    Method method = bean.getDeclaredMethod(methodName);
                    Object fieldValue = method.invoke(entity);
                    map.put(field.getName(), fieldValue);
                }
            } catch (Exception e) {
                // log.warn("toLinkedHashMap() Exception={}", e.getMessage());
            }
        }
        return map;
    }

    /**
     * 类转map
     * @param entity
     * @param <T>
     * @return
     */
    public static<T> Map<String, Object> toMap(T entity){
        Class<? extends Object> bean = entity.getClass();
        Field[] fields = bean.getDeclaredFields();
        Map<String, Object> map = new HashMap<>(fields.length);
        for(Field field:fields){
            try {
                if(!"serialVersionUID".equals(field.getName())){
                    String methodName = "get"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
                    Method method = bean.getDeclaredMethod(methodName);
                    Object fieldValue = method.invoke(entity);
                    map.put(field.getName(),fieldValue);
                }
            } catch (Exception e) {
//                log.warn("toMap() Exception={}",e.getMessage());
            }
        }
        return map;
    }
}
