package com.limu.user;

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
 * @createTime: 2024年01月13日 15:58
 */

//代码生成器

public class CodeGenerator {
    public static void main(String[] args) {
            String url = "jdbc:mysql:///colleges_user?useSSL=false&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8";
            String username = "root";
            String password = "root123";
            String author = "limu"; // 作者
            // 输出目录
            String outputDir = "D:\\桌面文件\\存放\\练习项目\\main\\one\\colleges_service\\colleges_service_service\\colleges_service_user\\src\\main\\java";
            // 选择父包名
            String basePackage = "com.limu.user";
            // 子包
            String moduleName = "sys";
            // 映射文件
            String mapperLocation = "D:\\桌面文件\\存放\\练习项目\\main\\one\\colleges_service\\colleges_service_service\\colleges_service_user\\src\\main\\resources\\mapper\\" + moduleName;
            // 数据库表名
            String tableName = "z_user,z_menu,z_role,z_role_menu,z_user_role,z_user_fan,z_user_follow,z_user_realname";
            // 去掉头
            String tablePrefix = "z_";
            FastAutoGenerator.create(url, username, password)
                    .globalConfig(builder -> {
                        builder.author(author) // 设置作者
                                //.enableSwagger() // 开启 swagger 模式
                                //.fileOverride() // 覆盖已生成文件
                                .outputDir(outputDir); // 指定输出目录
                    })
                    .packageConfig(builder -> {
                        builder.parent(basePackage) // 设置父包名
                                .moduleName(moduleName) // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, mapperLocation)); // 设置mapperXml生成路径
                    })
                    .strategyConfig(builder -> {
                        builder.addInclude(tableName) // 设置需要生成的表名
                                .addTablePrefix(tablePrefix); // 设置过滤表前缀
                    })
                    .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                    .execute();
        }
}
