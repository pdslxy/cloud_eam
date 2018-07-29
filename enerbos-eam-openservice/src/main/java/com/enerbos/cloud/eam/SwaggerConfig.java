package com.enerbos.cloud.eam;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author mp1
 * @version 1.0
 * @date 2017-01-03 18:41
 * @Description
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket swaggerSpringMvcPlugin() {
    	ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();  
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).defaultValue("Bearer 9c816649-6ca0-4b48-b4a6-b92dd8885869").build();  
        pars.add(tokenPar.build());  
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .paths(regex("/eam.*"))
                .build().globalOperationParameters(pars);
    }


    private ApiInfo apiInfo() {
        Contact contact = new Contact("翼虎能源", "www.enerbos.com", "dev@enerbos.com");
        return new ApiInfo(
                "EAM相关服务",//大标题
                "提供EAM相关服务",//小标题
                "1.0.0",//版本
                "NO terms of service",
                contact,//作者
                "",//链接显示文字
                ""//网站链接
        );
    }
}
