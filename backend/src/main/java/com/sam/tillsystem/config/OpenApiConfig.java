package com.sam.tillsystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(
        title = "Till API",
        version = "v1",
    description = "This app provides REST APIs for all associated till calls. From creating pages, products to transaction"))
public class OpenApiConfig {

}
