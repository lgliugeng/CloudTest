package com.liugeng.cloud.common.config;

import org.springframework.beans.factory.annotation.Value;
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
public class SwaggerConfig {

    //是否开启swagger，正式环境一般是需要关闭的，可根据springboot的多环境配置进行设置
    @Value(value = "${swagger2.conf.enable}")
    Boolean swaggerEnabled;

    @Value(value = "${swagger2.conf.title}")
    String title;

    @Value(value = "${swagger2.conf.description}")
    String description;

    @Value(value = "${swagger2.conf.version}")
    String version;

    @Value(value = "${swagger2.conf.basePackage}")
    String basePackage;

    @Value(value = "${swagger2.conf.contact-name}")
    String contact_name;

    @Value(value = "${swagger2.conf.contact-url}")
    String contact_url;

    @Value(value = "${swagger2.conf.contact-email}")
    String contact_email;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 是否开启
                .enable(swaggerEnabled).select()
                // 扫描的路径包
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any()).build().pathMapping("/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                // 作者信息
                .contact(new Contact(contact_name,contact_url,contact_email))
                .version(version)
                .build();
    }
}
