package br.com.icaro.paixao.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {


    // Para filtrar as REQUISIÇÕES

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();


        // aplicando oque eu quero filtrar.
        SimpleFilterProvider filter = new SimpleFilterProvider()
                .addFilter("PersonFilter" ,
                        SimpleBeanPropertyFilter.serializeAllExcept("sensitiveData"));

        // adicionando o filtro desejado no mapper
        mapper.setFilterProvider(filter);
        return mapper;

    }

}
