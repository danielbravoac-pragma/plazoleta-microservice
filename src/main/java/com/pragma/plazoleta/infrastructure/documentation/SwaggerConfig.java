package com.pragma.plazoleta.infrastructure.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Plazoleta")
                        .version("1.0")
                        .description("Documentación de la API para restaurantes y platos")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("soporte@empresa.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
