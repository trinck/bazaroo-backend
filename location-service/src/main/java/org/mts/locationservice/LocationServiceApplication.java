package org.mts.locationservice;

import org.mts.locationservice.entities.City;
import org.mts.locationservice.entities.Country;
import org.mts.locationservice.entities.Street;
import org.mts.locationservice.services.ICityService;
import org.mts.locationservice.services.ICountryService;
import org.mts.locationservice.services.IStreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class LocationServiceApplication implements CommandLineRunner {


    @Autowired
    private ICountryService iCountryService;
    @Autowired
    private ICityService iCityService;
    @Autowired
    private IStreetService iStreetService;
    public static void main(String[] args) {
        SpringApplication.run(LocationServiceApplication.class, args);
    }

    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        Country country = Country.builder()
                .name("Gabon")
                .code("GAB")
                .cities(new ArrayList<>())
                .build();
        List<City> cities = new ArrayList<>();
        City lbv = City.builder()
                .name("LIBREVILLE")
                .country(country)
                .build();
        Street charbonnage = Street.builder().city(lbv).name("Charbonnage").build();
        lbv.setStreets(List.of(charbonnage));

        String[] citiesNames = {"FRANCEVILLE","PORT-GENTIL", "MAKOKOU","KOULAMOUTOU", "TCHIBANGA"};
        country.getCities().add(lbv);
        for (String citiesName : citiesNames) {
            City city = City.builder()
                    .name(citiesName)
                    .country(country)
                    .build();
            cities.add(city);

        }

        country.getCities().addAll(cities);
        this.iCountryService.save(country);
    }
}
