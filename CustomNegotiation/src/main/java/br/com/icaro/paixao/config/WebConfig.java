package br.com.icaro.paixao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Conifgurações para utilizar CustomNegotiation no Projeto

@Configuration
public class WebConfig  implements WebMvcConfigurer {


    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        /**
        // VIA QUERY PARAM http://localhost:8080/api/test/V1/5?mediaType=xml
        configurer.favorParameter(true)
                .parameterName("mediaType")
                .ignoreAcceptHeader(true)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML);

         */

        // VIA HEADER PARAM http://localhost:8080/api/test/V1/5
        // escolher o formato do dado pelo header do postman
        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("yaml", MediaType.APPLICATION_YAML);

    }
}
