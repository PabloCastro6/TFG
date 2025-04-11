package com.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Configuración CORS (Cross-Origin Resource Sharing) para permitir
 * que el frontend (app en Vue.js) se comunique con
 * este backend Spring Boot desde otro origen.
 * 
 * 
 * <p>Permite solicitudes desde {@code http://localhost:8081}, que es
 * donde corre el frontend en desarrollo.</p>
 * 
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permite todas las rutas
                        .allowedOrigins("http://localhost:8081") // Tu frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
                        .allowedHeaders("*") ///Todos los headers
                        .allowCredentials(true); //Envio de cookies 
            }
        };
    }
}
