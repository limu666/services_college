# 智慧校园系统的设计与实现

## 系统页面

登录页面：![Alt](http://wptuchuang.limu.zone/wp_pics/person/sysP1.png)

主页面：![Alt](http://wptuchuang.limu.zone/wp_pics/person/sysP2.png)

## 使用工具说明
>1. mysql8.0.12
>2. 接口工具 postman，swagger3.0， knife4j
>3. 虚拟机 nacos ，redis
>4. java springboot2.7.6  jdk1.8
>5.

## 接口工具postman、swagger、knife4j

### postman

Postman是一款功能强大的网页调试与发送网页HTTP请求的Chrome插件。postman被500万开发者和超100,000家公司用于每月访问1.3亿个API。

官方网址：https://www.postman.com/

解压资料文件夹中的软件，安装即可

通常的接口测试查看请求和响应，下面是登录请求的测试


### swagger

[swagger-user](http://localhost:10001/swagger-ui/index.html#/)
[swagger-content](http://localhost:10002/swagger-ui/index.html#/)

(1)简介

Swagger 是一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务(<https://swagger.io/>)。 它的主要作用是：

1. 使得前后端分离开发更加方便，有利于团队协作

2. 接口的文档在线自动生成，降低后端开发人员编写接口文档的负担

3. 功能测试

   Spring已经将Swagger纳入自身的标准，建立了Spring-swagger项目，现在叫Springfox。通过在项目中引入Springfox ，即可非常简单快捷的使用Swagger。

(2)SpringBoot集成Swagger

- 引入依赖,在limu-leadnews-model和limu-leadnews-common模块中引入该依赖

  ```xml
  <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger2</artifactId>
  </dependency>
  <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger-ui</artifactId>
  </dependency>
  ```

只需要在limu-leadnews-common中进行配置即可，因为其他微服务工程都直接或间接依赖即可。

- 在limu-leadnews-common工程中添加一个配置类

新增：com.limu.common.swagger.SwaggerConfiguration

```java
package com.limu.common.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

   @Bean
   public Docket buildDocket() {
      return new Docket(DocumentationType.SWAGGER_2)
              .apiInfo(buildApiInfo())
              .select()
              // 要扫描的API(Controller)基础包
              .apis(RequestHandlerSelectors.basePackage("com.limu"))
              .paths(PathSelectors.any())
              .build();
   }

   private ApiInfo buildApiInfo() {
      Contact contact = new Contact("黑马程序员","","");
      return new ApiInfoBuilder()
              .title("黑马头条-平台管理API文档")
              .description("黑马头条后台api")
              .contact(contact)
              .version("1.0.0").build();
   }
}
```

在limu-leadnews-common模块中的resources目录中新增以下目录和文件

文件：resources/META-INF/Spring.factories

```java
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  com.limu.common.swagger.SwaggerConfiguration
```

（3）Swagger常用注解

在Java类中添加Swagger的注解即可生成Swagger接口文档，常用Swagger注解如下：

@Api：修饰整个类，描述Controller的作用

@ApiOperation：描述一个类的一个方法，或者说一个接口

@ApiParam：单个参数的描述信息

@ApiModel：用对象来接收参数

@ApiModelProperty：用对象接收参数时，描述对象的一个字段

@ApiResponse：HTTP响应其中1个描述

@ApiResponses：HTTP响应整体描述

@ApiIgnore：使用该注解忽略这个API

@ApiError ：发生错误返回的信息

@ApiImplicitParam：一个请求参数

@ApiImplicitParams：多个请求参数的描述信息



@ApiImplicitParam属性：

| 属性         | 取值   | 作用                                          |
| ------------ | ------ | --------------------------------------------- |
| paramType    |        | 查询参数类型                                  |
|              | path   | 以地址的形式提交数据                          |
|              | query  | 直接跟参数完成自动映射赋值                    |
|              | body   | 以流的形式提交 仅支持POST                     |
|              | header | 参数在request headers 里边提交                |
|              | form   | 以form表单的形式提交 仅支持POST               |
| dataType     |        | 参数的数据类型 只作为标志说明，并没有实际验证 |
|              | Long   |                                               |
|              | String |                                               |
| name         |        | 接收参数名                                    |
| value        |        | 接收参数的意义描述                            |
| required     |        | 参数是否必填                                  |
|              | true   | 必填                                          |
|              | false  | 非必填                                        |
| defaultValue |        | 默认值                                        |

我们在ApUserLoginController中添加Swagger注解，代码如下所示：

```java
@RestController
@RequestMapping("/api/v1/login")
@Api(value = "app端用户登录", tags = "ap_user", description = "app端用户登录API")
public class ApUserLoginController {

    @Autowired
    private ApUserService apUserService;

    @PostMapping("/login_auth")
    @ApiOperation("用户登录")
    public ResponseResult login(@RequestBody LoginDto dto){
        return apUserService.login(dto);
    }
}
```

LoginDto

```java
@Data
public class LoginDto {

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号",required = true)
    private String phone;

    /**
     * 密码
     */
    @ApiModelProperty(value="密码",required = true)
    private String password;
}
```

[启动user微服务，访问地址：](http://localhost:10001/swagger-ui.html)

### swagger说明

[swagger](http://localhost:10001/swagger-ui/index.html#/)
[视频链接](https://www.bilibili.com/video/BV1Yt4y1N7Un/?spm_id_from=333.880.my_history.page.click&vd_source=2bc1ee2c0ec9c64073a2e23934228a9e)
1.springboot 整合swagger3.0
第一步：导入依赖我的springboot为2.7.6
<parent>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-parent</artifactId>
<version>2.7.6</version>
</parent>

    yml配置文件
```yml
    spring:
      mvc:
        pathmatch:
          matching-strategy: ant_path_matcher
```
    swagger依赖
```xml
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-boot-starter</artifactId>
        <version>${swagger.version}</version>
    </dependency>
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-spring-web</artifactId>
        <version>${swagger.version}</version>
        </dependency>
    <dependency>
    <groupId>io.springfox</groupId>
         <artifactId>springfox-swagger-ui</artifactId>
         <version>${swagger.version}</version>
    </dependency>
```

    配置文件
```java
    package com.limu.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * TODO 类描述
 *
 * @author: LiMu
 * @createTime: 2023年06月20日 12:22*/

@EnableOpenApi
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket createRestApi() {
        //设置表示swagger的环境
        //Profiles of = Profiles.of("dev", "test");
        //判断当前是否处于该环境
        //通过 enable()接收此参数判断是否正确
        //boolean b = environment.acceptsProfiles(of);
        Docket docket = new Docket(DocumentationType.OAS_30);
        docket.apiInfo(apiInfo())
                .select()
                // 要扫描的API(Controller)基础包
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                // 新增token验证功能
                .securitySchemes(Collections.singletonList(securityScheme()))
                   .securityContexts(Collections.singletonList(securityContext()));
        return docket;
    }

    private SecurityScheme securityScheme(){
        return new ApiKey("Z-Token", "Z-Token", "header");
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("^(?!auth).*$"))
                .build();
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("Z-Token", authorizationScopes));

    }

    /**
     * 增加如下配置可解决Spring Boot 2.6以上 与Swagger 3.0.0 不兼容问题
     **/
    @Bean
    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping
                    (WebEndpointsSupplier webEndpointsSupplier, ServletEndpointsSupplier servletEndpointsSupplier,
                     ControllerEndpointsSupplier controllerEndpointsSupplier, EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties, WebEndpointProperties webEndpointProperties, Environment environment) {
        List<ExposableEndpoint<?>> allEndpoints = new ArrayList();
        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
        String basePath = webEndpointProperties.getBasePath();
        EndpointMapping endpointMapping = new EndpointMapping(basePath);
        boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping, null);
    }
    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment, String basePath) {
        return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
    }
    /*@Bean
    public Docket buildDocket(Environment environment) {
        //设置表示swagger的环境
        Profiles of = Profiles.of("dev", "test");
        //判断当前是否处于该环境
        //通过 enable()接收此参数判断是否正确
        boolean b = environment.acceptsProfiles(of);
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(buildApiInfo())
                .enable(b)
                .select()
                // 要扫描的API(Controller)基础包
                .apis(RequestHandlerSelectors.basePackage("com.limu"))
                .paths(PathSelectors.any())
                .build();
    }*/

    /*private ApiInfo buildApiInfo() {
        Contact contact = new Contact("李牧","http://localhost:10001/colleg/user/login","2495853587@qq.com");
        return new ApiInfoBuilder()
                .title("智慧校园-平台管理API文档")
                .description("智慧校园后台api")
                .contact(contact)
                .version("1.0.0").build();
    }*/

    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo()
    {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                .title("简单Web接口文档")
                .description("描述：用于管理接口信息,具体包括XXX,XXX模块...")
                .termsOfServiceUrl("http://localhost:10001/swagger-ui/index.html")
                .contact(new Contact("limu", null, "2495853587@qq.com"))
                .version("版本号:0.1")
                .build();
    }

    /*@Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                        .filter(mapping -> mapping.getPatternParser() == null)
                        .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }
*/
}
```

### knife4j

在浏览器输入地址：`http://localhost:10001/doc.html`

说明：springboot2.7连knife4j在写好swagger的前提下直接导入依赖即可，无需再写配置类

(1)简介

knife4j是为Java MVC框架集成Swagger生成Api文档的增强解决方案,前身是swagger-bootstrap-ui,取名kni4j是希望它能像一把匕首一样小巧,轻量,并且功能强悍!

gitee地址：https://gitee.com/xiaoym/knife4j

官方文档：https://doc.xiaominfo.com/

效果演示：http://knife4j.xiaominfo.com/doc.html

(2)核心功能

该UI增强包主要包括两大核心功能：文档说明 和 在线调试

- 文档说明：根据Swagger的规范说明，详细列出接口文档的说明，包括接口地址、类型、请求示例、请求参数、响应示例、响应参数、响应码等信息，使用swagger-bootstrap-ui能根据该文档说明，对该接口的使用情况一目了然。
- 在线调试：提供在线接口联调的强大功能，自动解析当前接口参数,同时包含表单验证，调用参数可返回接口响应内容、headers、Curl请求命令实例、响应时间、响应状态码等信息，帮助开发者在线调试，而不必通过其他测试工具测试接口是否正确,简介、强大。
- 个性化配置：通过个性化ui配置项，可自定义UI的相关显示信息
- 离线文档：根据标准规范，生成的在线markdown离线文档，开发者可以进行拷贝生成markdown接口文档，通过其他第三方markdown转换工具转换成html或pdf，这样也可以放弃swagger2markdown组件
- 接口排序：自1.8.5后，ui支持了接口排序功能，例如一个注册功能主要包含了多个步骤,可以根据swagger-bootstrap-ui提供的接口排序规则实现接口的排序，step化接口操作，方便其他开发者进行接口对接

(3)快速集成

- 在limu-leadnews-common模块中的`pom.xml`文件中引入`knife4j`的依赖,如下：

```xml
<dependency>
     <groupId>com.github.xiaoymin</groupId>
     <artifactId>knife4j-spring-boot-starter</artifactId>
</dependency>
```

- 创建Swagger配置文件

在limu-leadnews-common模块中新建配置类

新建Swagger的配置文件`SwaggerConfiguration.java`文件,创建springfox提供的Docket分组对象,代码如下：

```java
package com.limu.common.knife4j;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class Swagger2Configuration {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //分组名称
                .groupName("1.0")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.limu"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("黑马头条API文档")
                .description("黑马头条API文档")
                .version("1.0")
                .build();
    }
}
```

以上有两个注解需要特别说明，如下表：

| 注解              | 说明                                                         |
| ----------------- | ------------------------------------------------------------ |
| `@EnableSwagger2` | 该注解是Springfox-swagger框架提供的使用Swagger注解，该注解必须加 |
| `@EnableKnife4j`  | 该注解是`knife4j`提供的增强注解,Ui提供了例如动态参数、参数过滤、接口排序等增强功能,如果你想使用这些增强功能就必须加该注解，否则可以不用加 |

- 添加配置

在Spring.factories中新增配置

```java
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  com.limu.common.swagger.Swagger2Configuration, \
  com.limu.common.swagger.SwaggerConfiguration
```

- 访问

在浏览器输入地址：`http://host:port/doc.html`

## 网关

### Whitelabel Error Page 错误

This application has no configured error view, so you are seeing this as a fallback.

Mon Jul 17 11:32:39 CST 2023
[5dc205b9-5] There was an unexpected error (type=Service Unavailable, status=503).

### nacos配置列表

> 链接：http://192.168.22.135:8848/nacos/index.html#/configurationManagement?dataId=&group=&appName=&namespace=&pageSize=&pageNo=

#### colleges-user
```yml
spring:
  jackson:
    # 设置时区
    # 中国北京时区 = 格林时间 + 8小时。其中GMT 代表的就是 格林时间
    # 北京市 是东八区 的 ，与本初子午线上的时间，相差 8小时
    time-zone: GMT+8
    # 设置日期的格式 是 年月日时分秒
    date-format: yyyy-MM-dd HH:mm:ss
  datasource: 
    username: root
    password: root123
    url: jdbc:mysql:///colleges_user?useSSL=false&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8
    driver-class-name: com.mysql.cj.jdbc.Driver
# 设置Mapper接口所对应的XML文件位置，如果你在Mapper接口中有自定义方法，需要进行该配置
mybatis-plus:
  mapper-locations: classpath*:mapper/*.xml
  # 设置别名包扫描路径，通过该属性可以给包中的类注册别名
  type-aliases-package: com.limu.model.user.pojos
  configuration:
    map-underscore-to-camel-case: true #开启驼峰命名规则

```
#### colleges_service_app_gateway

```yml
spring:
  cloud:
    gateway:
      globalcors:
        add-to-simple-url-handler-mapping: true # 解决options请求被拦截问题
        corsConfigurations:
          '[/**]':
            allowedHeaders: "*"  #允许跨域的头
            allowedOrigins: "http://localhost:8088"  #跨域处理 允许所有的域
            allowedMethods:      #支持的方法
              - GET
              - POST
              - DELETE
              - PUT
              - OPTION
            allowCredentials: true # 是否允许携带cookie
            maxAge: 360000 # 这次跨域检测的有效期
      routes:
        # 平台管理
        - id: user  # 路由标识，必须唯一
          uri: lb://colleges-user # 路由的目标地址，lb为load balance，负载均衡
          predicates:
            - Path=/user/**     # 判断请求是否以/user开头
          filters:
            - StripPrefix= 1
mybatis-plus:
  global-config:
    db-config:
      logic-delete-field: deleted #    设置全局删除的实体字段名
      logic-not-delete-value: 0 # 逻辑未删除值（默认为0）
      logic-delete-value: 1 # 逻辑已删除值（默认为1）

```

#### colleges-content

```yml

spring:
  jackson:
    # 设置时区
    # 中国北京时区 = 格林时间 + 8小时。其中GMT 代表的就是 格林时间
    # 北京市 是东八区 的 ，与本初子午线上的时间，相差 8小时
    time-zone: GMT+8
    # 设置日期的格式 是 年月日时分秒
    date-format: yyyy-MM-dd HH:mm:ss
  datasource: 
    username: root
    password: root123
    url: jdbc:mysql:///colleges_content?useSSL=false&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8
    driver-class-name: com.mysql.cj.jdbc.Driver
# 设置Mapper接口所对应的XML文件位置，如果你在Mapper接口中有自定义方法，需要进行该配置
mybatis-plus:
  mapper-locations: classpath*:mapper/sys/*.xml
  # 设置别名包扫描路径，通过该属性可以给包中的类注册别名
  type-aliases-package: com.limu.model.content.pojos
  configuration:
    map-underscore-to-camel-case: true #开启驼峰命名规则


```

### 配置网关

1. 导入依赖pom
```xml
    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bootstrap</artifactId>
        </dependency>

        <!--客户端负载均衡loadbalancer-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-loadbalancer</artifactId>
        </dependency>

    </dependencies>

```
2. yaml配置
```yaml
server:
  port: 11001
spring:
  application:
    name: colleges_service_app_gateway
  cloud:
    nacos:
      discovery:
        server-addr: 192.168.22.135:8848
      config:
        server-addr: 192.168.22.135:8848
        file-extension: yml


```
3. 启动
```java
package com.limu.app.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient  //开启注册中心
@SpringBootApplication
public class CollegesServiceAppGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollegesServiceAppGatewayApplication.class, args);
    }

}

```

4. nacos配置
```yaml
spring:
  cloud:
    gateway:
      globalcors:
        #add-to-simple-url-handler-mapping: true
        corsConfigurations:
          '[/**]':
            #allowedHeaders: "*"  
            allowedOrigins: "*"  #跨域处理 允许所有的域
            allowedMethods:      #支持的方法
              - GET
              - POST
              - DELETE
              - PUT
              - OPTION
      routes:
        # 平台管理
        - id: user  # 路由标识，必须唯一
          uri: lb://colleges-user # 路由的目标地址，lb为load balance，负载均衡
          predicates:
            - Path= /user/**     # 判断请求是否以/user开头
          filters:
            - StripPrefix= 1

        - id: content  # 路由标识，必须唯一
          uri: lb://colleges-content # 路由的目标地址，lb为load balance，负载均衡
          predicates:
            - Path= /content/**     # 判断请求是否以/content开头
          filters:
            - StripPrefix= 1
```
## 数据库表说明

### colleges_user

| **表名称**    | **说明**         |
| --------------| ---------------  |
| z_user          | 用户信息表     |
| z_user_role     | 用户角色信息表 |
| z_role          | 角色信息表 |
| z_user_realname | 实名认证信息表 |
| z_role_menu     | 角色菜单信息表 |
| z_menu          | 菜单信息表 |

### colleges_content

| **表名称**    | **说明**         |
| --------------| ---------------  |
| z_category    | 文章分类表     |
| z_content     | 文章内容表 |








>**山重水复疑无路，柳暗花明又一村!**
