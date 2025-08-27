package com.hoang.hoangtourapi.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openApi(): OpenAPI {
        val servers = listOf(Server().url("/"))

        val accessTokenScheme =
            SecurityScheme().apply {
                name = "Authorization"
                type = SecurityScheme.Type.HTTP
                scheme = "bearer"
                bearerFormat = "JWT"
            }

        val refreshTokenScheme =
            SecurityScheme().apply {
                name = "Refresh-Token"
                type = SecurityScheme.Type.APIKEY
                `in` = SecurityScheme.In.HEADER
            }

        return OpenAPI()
            .info(
                Info().apply {
                    title = "hoang-tour api"
                    version = "1.0"
                    description = "hoang-tour api server"
                    contact = Contact().email("woocurlee@gmail.com")
                },
            )
            .servers(servers)
            .addSecurityItem(SecurityRequirement().addList("bearerAuth"))
            .components(
                Components()
                    .addSecuritySchemes("bearerAuth", accessTokenScheme)
                    .addSecuritySchemes("refreshToken", refreshTokenScheme),
            )
    }
}
