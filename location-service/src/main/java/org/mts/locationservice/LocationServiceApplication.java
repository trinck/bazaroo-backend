package org.mts.locationservice;

import jakarta.transaction.Transactional;
import org.mts.locationservice.entities.City;
import org.mts.locationservice.entities.Country;
import org.mts.locationservice.entities.Street;
import org.mts.locationservice.repositories.CountryRepository;
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
    @Autowired
    private CountryRepository countryRepository;
    public static void main(String[] args) {
        SpringApplication.run(LocationServiceApplication.class, args);
    }

    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        boolean exists = this.countryRepository.existsCountryByNameEqualsIgnoreCase("Gabon");
        if(exists){
            return;
        }
        Country country = Country.builder()
                .name("Gabon")
                .code("GA")
                .cities(new ArrayList<>())
                .build();

        Country countryTd = Country.builder()
                .name("Tchad")
                .code("TD")
                .cities(new ArrayList<>())
                .build();
        List<City> cities = new ArrayList<>();
        City lbv = City.builder()
                .name("LIBREVILLE")
                .country(country)
                .build();
        City moundou = City.builder()
                .name("MOUNDOU")
                .country(countryTd)
                .build();
        Street charbonnage = Street.builder().city(lbv).name("Charbonnage").build();
        lbv.setStreets(List.of(charbonnage));

        Street Giekol = Street.builder().city(moundou).name("Giekol").build();
        moundou.setStreets(List.of(Giekol));

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
        countryTd.getCities().add(moundou);
        this.iCountryService.save(country);
        this.iCountryService.save(countryTd);
    }
}
