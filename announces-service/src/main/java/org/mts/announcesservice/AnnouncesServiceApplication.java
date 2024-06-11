package org.mts.announcesservice;

import org.mts.announcesservice.entities.AnnounceType;
import org.mts.announcesservice.entities.Category;
import org.mts.announcesservice.entities.CategoryField;
import org.mts.announcesservice.enums.FieldType;
import org.mts.announcesservice.service.IAnnounceTypeService;
import org.mts.announcesservice.service.ICategoryFieldService;
import org.mts.announcesservice.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class AnnouncesServiceApplication implements CommandLineRunner {

    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IAnnounceTypeService announceTypeService;
    @Autowired
    private ICategoryFieldService categoryFieldService;

    public static void main(String[] args) {
        SpringApplication.run(AnnouncesServiceApplication.class, args);
    }

    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        Category category = Category.builder()
                .title("Computer")
                .iconUrl("image")
                .description("Buy or sell un computer")
                .build();
        AnnounceType type = AnnounceType.builder()
                .name("Order")
                .build();

        CategoryField field = CategoryField.builder()
                .fieldName("ram")
                .type(FieldType.SHORT_TEXT)
                .build();

        category = this.categoryService.create(category);
        type.setCategory(category);
        type = this.announceTypeService.create(type);
        field.setAnnounceType(type);
        field = this.categoryFieldService.create(field);
    }
}
