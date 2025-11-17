package com.mlbyl.usermanagementservicetask.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    public SwaggerConfig() {
        System.out.println("!!! SwaggerConfig constructor called !!!");
    }

    @Bean
    public OpenAPI openAPI() {  // Əvvəlki "customOpenAPI" yerinə "openAPI"
        System.out.println("!!! openAPI bean is being created !!!");
        return new OpenAPI()
                .info(new Info()
                        .title("User Management Service API")
                        .version("1.0")
                        .description("User Management Service CRUD operations")
                        .contact(new Contact()
                                .name("Mahammad")
                                .email("mahammadalibayli@gmail.com")
                        )
                )
                .addServersItem(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Server")
                );
    }

}
