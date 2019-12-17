package com.liugeng.cloud.common.config;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ClassUtils;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

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

    /**多配置路径分隔符*/
    private static final String splitor = ";";

    @Bean
    public Docket createRestApi() {
        List<Parameter> parameters = Collections.singletonList(
                new ParameterBuilder().name(HttpHeaders.AUTHORIZATION)
                        .description("Access Token")
                        .modelRef(new ModelRef("String"))
                        .defaultValue("")
                        .parameterType("header")
                        .required(false)
                        .build()
        );
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 是否开启
                .enable(swaggerEnabled).select()
                // 扫描的路径包
                //.apis(RequestHandlerSelectors.basePackage(basePackage))
                .apis(basePackage(basePackage))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any()).build().pathMapping("/");
        //.globalOperationParameters(parameters);替换pathMapping
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

    /**
     * 多路径包扫描
     *
     * @param basePackage 请求路径

     * @return    com.google.common.base.Predicate<springfox.documentation.RequestHandler>
     * @author    liugeng
     * @date      2019/12/17 10:14
     */
    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(splitor)) {
                boolean isMatch = ClassUtils.getPackageName(input).startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }
}
