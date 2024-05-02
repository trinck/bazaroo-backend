package org.mts.locationservice.web;

import jakarta.ws.rs.NotFoundException;
import org.modelmapper.ModelMapper;
import org.mts.locationservice.dtos.CountryInputDTO;
import org.mts.locationservice.dtos.CountryOutputDTO;
import org.mts.locationservice.entities.Country;
import org.mts.locationservice.services.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("countries")
public class CountryController {

    @Autowired
    private ICountryService iCountryService;
    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("/index/{id}")
    public CountryOutputDTO getCountryById(@PathVariable String id){

        Country country = this.iCountryService.getCountryById(id);

        if(country == null){
            throw new NotFoundException("Country with id:"+id+" doesn't exists");
        }

        return modelMapper.map(country, CountryOutputDTO.class);
    }

    @GetMapping("/delete/{id}")
    public CountryOutputDTO deleteCountryById(@PathVariable String id){

        Country country = this.iCountryService.deleteCountryById(id);

        if(country == null){
            throw new NotFoundException("Country with id:"+id+" doesn't exists");
        }

        return convertToOutputDto(country);
    }


    @GetMapping("/countries")
    public List<CountryOutputDTO> getAllCountries(){
        List<Country> countries = this.iCountryService.getCountries();
      return  countries.stream().map(this::convertToOutputDto).toList();

    }


    @PostMapping(value = "/save")
    public CountryOutputDTO addCountry(@RequestBody CountryInputDTO countryInputDTO){

        Country country = modelMapper.map(countryInputDTO, Country.class);
        countryInputDTO.setId(this.iCountryService.addCountry(country).getId());

        return convertToOutputDto( country);
    }


    private CountryOutputDTO convertToOutputDto(Country country) {
        return modelMapper.map(country, CountryOutputDTO.class);
    }

    private CountryInputDTO convertToInputDto(Country country) {
        return modelMapper.map(country, CountryInputDTO.class);
    }

    private Country convertToEntity(CountryInputDTO countryInputDTO) {
        return modelMapper.map(countryInputDTO, Country.class);
    }

    private Country convertToEntity(CountryOutputDTO countryOutputDTO) {
        return modelMapper.map(countryOutputDTO, Country.class);
    }
}
