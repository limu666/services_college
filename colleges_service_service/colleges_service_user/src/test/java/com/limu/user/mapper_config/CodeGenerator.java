package com.limu.user.mapper_config;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.sql.Types;
import java.util.Collections;

/**
 * TODO 类描述
 *
 * @author: LiMu
 * @createTime: 2023年06月06日 12:07
 */
//代码生成器
public class CodeGenerator {
    public static void main(String[] args) {
        String url = "jdbc:mysql:///colleges_user?useSSL=false&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8";
        String username = "root";
        String password = "root123";
        String moduleName = "manage";
        String mapperLocation = "D:\\桌面文件\\存放\\练习项目\\main\\one\\colleges_service\\colleges_service_service\\colleges_service_user\\src\\main\\resources\\mapper" + moduleName;
        String tables = "user";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("limu") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            //.fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\桌面文件\\存放\\练习项目\\main\\one\\colleges_service\\colleges_service_service\\colleges_service_user\\src\\main\\java"); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("com.limu.user") // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperLocation)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }

}
