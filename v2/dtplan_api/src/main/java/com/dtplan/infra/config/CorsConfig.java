package com.dtplan.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@Profile("dev") // OU prod
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedOrigins("http")
                .allowedOrigins("https")
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}