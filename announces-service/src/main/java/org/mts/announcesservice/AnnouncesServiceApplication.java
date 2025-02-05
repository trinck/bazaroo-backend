package org.mts.announcesservice;

import org.mts.announcesservice.configs.ElasticsearchConfig;
import org.mts.announcesservice.entities.AnnounceType;
import org.mts.announcesservice.entities.Category;
import org.mts.announcesservice.entities.CategoryField;
import org.mts.announcesservice.enums.FieldType;
import org.mts.announcesservice.repositories.CategoryRepository;
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
    @Autowired
    private ElasticsearchConfig elasticsearchConfig;
    @Autowired
    private CategoryRepository categoryRepository;
    public static void main(String[] args) {
        SpringApplication.run(AnnouncesServiceApplication.class, args);
    }

    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        boolean computer = this.categoryRepository.existsCategoryByTitleEqualsIgnoreCase("Computer");
        if (computer)
        {
            return;
        }

        Category category = Category.builder()
                .title("Computer")
                .iconUrl("laptop")
                .color("#2543be")
                .description("Buy or sell un computer")
                .build();

        Category category1 = Category.builder()
                .title("Marché")
                .iconUrl("shop")
                .color("#9d25be")
                .description("Vent et achête des produits du marché Bazaroo")
                .build();

        AnnounceType typeC1 = AnnounceType.builder()
                .name("Vente")
                .build();

        AnnounceType type = AnnounceType.builder()
                .name("Order")
                .build();
        AnnounceType type1 = AnnounceType.builder()
                .name("Vente")
                .build();

        CategoryField field = CategoryField.builder()
                .fieldName("ram")
                .type(FieldType.SHORT_TEXT)
                .build();

        CategoryField field1 = CategoryField.builder()
                .fieldName("Capacité")
                .type(FieldType.SHORT_TEXT)
                .build();

        CategoryField field2 = CategoryField.builder()
                .fieldName("Taille de l'ecran")
                .type(FieldType.SHORT_TEXT)
                .build();

        category = this.categoryService.create(category);
        category1 = this.categoryService.create(category1);
        typeC1.setCategory(category1);
        type1.setCategory(category);
        type.setCategory(category);

        type1 = this.announceTypeService.create(type1);
        type = this.announceTypeService.create(type);
        typeC1 = this.announceTypeService.create(typeC1);

        field.setAnnounceType(type1);
        field1.setAnnounceType(type1);
        field2.setAnnounceType(type1);
        field = this.categoryFieldService.create(field);
        field1 = this.categoryFieldService.create(field1);
        field2 = this.categoryFieldService.create(field2);

        this.elasticsearchConfig.createIndex();
    }
}
