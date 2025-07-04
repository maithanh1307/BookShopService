package com.bookservice.employee.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition (
        info = @Info (
                title = "Employee Api Specification",
                description = "Api documentation for employee service",
                version = "1.0",
                contact = @Contact (
                        name = "Mai Thanh",
                        email = "nguyentranmaithanh1307@gmail.com",
                        url = "http://laptrinhfullstack.vercel.app"
                ),
                license = @License (
                        name = "MIT License",
                        url = "http://laptrinhfullstack.vercel.app/license"
                ),
                termsOfService = "http://laptrinhfullstack.vercel.app/terms"
        ),
        servers = {
                @Server (
                        description = "Local ENV",
                        url = "http://localhost:9002"
                ),
                @Server (
                        description = "Dev ENV",
                        url = "http://employee-service.dev.com"
                ),
                @Server (
                        description = "Prod ENV",
                        url = "http://employee-service.prod.com"
                )

        }
)
public class OpenApiConfig {
}
