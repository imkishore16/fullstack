package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.utils.MyConstants;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;



import java.util.*;
@Configuration
public class SwaggerApiConfig {
 @Bean
    public OpenAPI openApi(){
        return new OpenAPI().servers(List.of(new Server().url(MyConstants.SWAGGER_LOCALHOST_URL)))
                            .addSecurityItem(new SecurityRequirement()
                            .addList(MyConstants.SWAGGER_SECURITY_SCHEME_NAME))
                            .components(new Components().addSecuritySchemes(MyConstants.SWAGGER_SECURITY_SCHEME_NAME, new SecurityScheme().name(MyConstants.SWAGGER_SECURITY_SCHEME_NAME)
                            .type( Type.HTTP)
                            .scheme(MyConstants.SWAGGER_SCHEME)
                            .description(MyConstants.SWAGGER_DESCRIPTION)
                            .bearerFormat(MyConstants.SWAGGER_DESCRIPTION)));
    }
}