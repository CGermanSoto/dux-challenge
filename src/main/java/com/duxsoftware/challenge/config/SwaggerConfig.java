package com.duxsoftware.challenge.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Duxsoftware")
                        .version("1.0")
                        .description("Documentación de la API Equipos para la prueba técnica de duxsoftware.com"));
    }
}
