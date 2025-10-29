package com.fiis.peliculas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    // para poder guardar imagenes en la carpeta uploads y poder acceder a ellas desde el navegador
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        String uploadPath = System.getProperty("user.dir") + "/uploads/";
        System.out.println("Upload Path: " + uploadPath);

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath);
    }

}