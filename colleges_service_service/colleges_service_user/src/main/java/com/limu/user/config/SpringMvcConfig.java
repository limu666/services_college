package com.limu.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:bootstrap.yml")
public class SpringMvcConfig implements WebMvcConfigurer {

    @Autowired
    private Environment environment;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern(("/upload/hdpic/**"))) {
            String fileName = environment.getProperty("upload.file.location");
            assert fileName != null;
            registry.addResourceHandler("/upload/hdpic/**").addResourceLocations("file:".concat(fileName));
        }
    }
}
