package org.mts.imagesservice.configs;


import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MVCConfig implements WebMvcConfigurer {



    private final StorageMediasSources mediasSources;

    public MVCConfig(StorageMediasSources mediasSources) {
        this.mediasSources = mediasSources;
    }

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // TODO Auto-generated method stub
        WebMvcConfigurer.super.addResourceHandlers(registry);
        exposeDirectory(mediasSources.getAdverts(), registry);
        exposeDirectory(mediasSources.getProfiles(), registry);
        exposeDirectory(mediasSources.getIcons(), registry);

    }


    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");

        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ uploadPath + "/");
    }

}
