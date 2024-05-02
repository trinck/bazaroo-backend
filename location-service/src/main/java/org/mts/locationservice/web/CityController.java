package org.mts.locationservice.web;


import jakarta.ws.rs.NotFoundException;
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
    @GetMapping("/index/{id}")
    public CityOutputDTO getCityById(@PathVariable String id){

        City city = this.iCityService.getCityById(id);

        if(city == null){
            throw new NotFoundException("City with id:"+id+" doesn't exists");
        }

        return convertToOutputDto(city);
    }

    @GetMapping("/delete/{id}")
    public CityOutputDTO deleteCityById(@PathVariable String id){

        City city = this.iCityService.deleteCiyById(id);

        if(city == null){
            throw new NotFoundException("City with id:"+id+" doesn't exists");
        }

        return convertToOutputDto(city);
    }


    @GetMapping("/cities")
    public List<CityOutputDTO> getAllCities(){
        List<City> cities = this.iCityService.getCities();
        return  cities.stream().map(this::convertToOutputDto).toList();

    }


    @PostMapping("/save")
    public CityOutputDTO addCity(@RequestBody CityInputDTO cityInputDTO){

        Country country = this.iCountryService.getCountryById(cityInputDTO.getCountryId());
        if(country == null){
            throw  new IllegalArgumentException("The city whose must receive the city doesn't exists");
        }

        City city = convertToEntity(cityInputDTO);
        city.setCountry(country);
        return convertToOutputDto( this.iCityService.addCity(city));
    }


    private CityOutputDTO convertToOutputDto(City city) {
        return modelMapper.map(city, CityOutputDTO.class);
    }

    private CityInputDTO convertToInputDto(City city) {
        return modelMapper.map(city, CityInputDTO.class);
    }

    private City convertToEntity(CityInputDTO cityInputDTO) {
        return modelMapper.map(cityInputDTO, City.class);
    }

    private City convertToEntity(CityOutputDTO cityOutputDTO) {
        return modelMapper.map(cityOutputDTO, City.class);
    }

}
