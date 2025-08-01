package org.mts.announcesservice;

import org.mts.announcesservice.configs.ElasticsearchConfig;
import org.mts.announcesservice.entities.*;
import org.mts.announcesservice.enums.FieldType;
import org.mts.announcesservice.repositories.CategoryRepository;
import org.mts.announcesservice.service.IAnnounceTypeService;
import org.mts.announcesservice.service.ICategoryFieldService;
import org.mts.announcesservice.service.ICategoryService;
import org.mts.announcesservice.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.List;

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

    @Autowired
    private SchedulerService schedulerService;
    public static void main(String[] args) {
        SpringApplication.run(AnnouncesServiceApplication.class, args);
    }

    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        boolean computer = this.categoryRepository.existsCategoryByTitleEqualsIgnoreCase("Immobilier");
        if (computer)
        {
            return;
        }

        Category category = Category.builder()
                .title("Immobilier")
                .iconUrl("fa-solid fa-city")
                .color("#009afa")
                .description("Acheter ou vendre un immobilier")
                .build();

        Category category1 = Category.builder()
                .title("MarketPlace")
                .iconUrl("fa-solid fa-cart-shopping")
                .color("#2fc967")
                .description("Acheter et vendre des produits du marché Adryvo")
                .build();

        Category category2 = Category.builder()
                .title("Véhicules")
                .iconUrl("fa-solid fa-car")
                .color("#ff5563")
                .description("Acheter et vendre des produits du marché Adryvo")
                .build();

        Category category3 = Category.builder()
                .title("Emplois et Services")
                .iconUrl("fa-solid fa-user-tie")
                .color("#36b5a4")
                .description("Acheter et vendre des produits du marché Adryvo")
                .build();

        AnnounceType typeC1 = AnnounceType.builder()
                .name("Vente")
                .build();

        AnnounceType type = AnnounceType.builder()
                .name("Location")
                .build();
        AnnounceType type1 = AnnounceType.builder()
                .name("Vente")
                .build();

        CategoryField field = CategoryField.builder()
                .fieldName("Chambre")
                .icon("pi pi-building-columns")
                .type(FieldType.SHORT_TEXT)
                .build();

        CategoryField field1 = CategoryField.builder()
                .fieldName("Douche")
                .type(FieldType.SHORT_TEXT)
                .icon("pi pi-gift")
                .build();

        CategoryField field2 = CategoryField.builder()
                .fieldName("Salon")
                .type(FieldType.SHORT_TEXT)
                .icon("pi pi-building")
                .build();

        CategoryFieldCheck field3 = new CategoryFieldCheck();
        field3.setFieldName("Meublé");
        field3.setType(FieldType.RADIO);
        field3.setIcon("pi pi-box");

        CategoryFieldCheckUnit check1 = CategoryFieldCheckUnit.builder()
                .checked(true)
                .name("Oui")
                .categoryField((CategoryFieldCheck) field3)
                .dataValue("Oui")
                .build();

        CategoryFieldCheckUnit check2 = CategoryFieldCheckUnit.builder()
                .checked(false)
                .name("Non")
                .categoryField((CategoryFieldCheck) field3)
                .dataValue("Non")
                .build();
        ((CategoryFieldCheck)field3).setFieldCheckUnits(List.of(check1,check2));

        category = this.categoryService.create(category);
        category1 = this.categoryService.create(category1);
        category2 = this.categoryService.create(category2);
        category3 = this.categoryService.create(category3);
        typeC1.setCategory(category1);
        type1.setCategory(category);
        type.setCategory(category);

        type1 = this.announceTypeService.create(type1);
        type = this.announceTypeService.create(type);
        typeC1 = this.announceTypeService.create(typeC1);

        field.setAnnounceType(type1);
        field1.setAnnounceType(type1);
        field2.setAnnounceType(type1);
        field3.setAnnounceType(type1);
        field = this.categoryFieldService.create(field);
        field1 = this.categoryFieldService.create(field1);
        field2 = this.categoryFieldService.create(field2);
         this.categoryFieldService.create(field3);

    }
}
