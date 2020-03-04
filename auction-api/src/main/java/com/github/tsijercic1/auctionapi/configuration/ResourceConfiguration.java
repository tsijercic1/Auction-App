package com.github.tsijercic1.auctionapi.configuration;

import com.github.tsijercic1.auctionapi.controllers.ProductController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class ResourceConfiguration implements WebMvcConfigurer {
    final FileStorageConfiguration fileStorageConfiguration;
    private static final Logger logger = LoggerFactory.getLogger(ResourceConfiguration.class);

    public ResourceConfiguration(FileStorageConfiguration fileStorageConfiguration) {
        this.fileStorageConfiguration = fileStorageConfiguration;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info(fileStorageConfiguration.getUploadDir());
        registry
                .addResourceHandler("/image/**")
                .addResourceLocations("file:"+fileStorageConfiguration.getUploadDir()+"/");
    }

}
