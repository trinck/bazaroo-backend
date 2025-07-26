package org.mts.locationservice.web;


import org.modelmapper.ModelMapper;
import org.mts.locationservice.dtos.CityInputDTO;
import org.mts.locationservice.dtos.CityOutputDTO;
import org.mts.locationservice.entities.City;
import org.mts.locationservice.entities.Country;
import org.mts.locationservice.services.ICityService;
import org.mts.locationservice.services.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cities")
public class CityController {


    @Autowired
    private ICityService iCityService;
    @Autowired
    private ICountryService iCountryService;
    @Autowired
    private ModelMapper modelMapper;
    @GetMapping("/{id}")
    public CityOutputDTO getCityById(@PathVariable String id){
        return convertToOutputDto(this.iCityService.getCityById(id));
    }

    @GetMapping("/country/{countryName}")
    public List<City> getCitiesByCountryName(@PathVariable String countryName){
        return this.iCityService.getCitiesByCountryName(countryName).stream().toList();
    }

    @DeleteMapping("/{id}")
    public CityOutputDTO deleteCityById(@PathVariable String id){
        return convertToOutputDto(this.iCityService.deleteCiyById(id));
    }


    @GetMapping
    public List<CityOutputDTO> getAllCities(){
        return  this.iCityService.getCities().stream().map(this::convertToOutputDto).toList();
    }

    @GetMapping("/withCountry")
    public List<City> getAllCitiesWithCountry(){
        return this.iCityService.getCities().stream().toList();
    }

    @PostMapping("/{countryId}")
    public CityOutputDTO addCity(@PathVariable String countryId, @RequestBody CityInputDTO cityInputDTO){

        Country country = this.iCountryService.getCountryById(countryId);
        City city = convertToEntity(cityInputDTO);
        city.setCountry(country);
        return convertToOutputDto( this.iCityService.saveCity(city));
    }




    private CityOutputDTO convertToOutputDto(City city) {
        return modelMapper.map(city, CityOutputDTO.class);
    }
    private City convertToEntity(CityInputDTO cityInputDTO) {
        return modelMapper.map(cityInputDTO, City.class);
    }

}
