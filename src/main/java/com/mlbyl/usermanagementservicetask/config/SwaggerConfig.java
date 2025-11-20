package com.mlbyl.usermanagementservicetask.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Value("${app.swagger.server-url}")
    private String serverUrl;

    @Bean
    public OpenAPI openAPI() {
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
                                .url(serverUrl)
                                .description("Local Server")
                );
    }

}
