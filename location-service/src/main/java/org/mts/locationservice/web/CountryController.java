package org.mts.locationservice.web;

import org.modelmapper.ModelMapper;
import org.mts.locationservice.configs.CountryContext;
import org.mts.locationservice.dtos.CountryInputDTO;
import org.mts.locationservice.dtos.CountryOutputDTO;
import org.mts.locationservice.entities.Country;
import org.mts.locationservice.services.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



import java.util.List;

@RestController
@RequestMapping("countries")
public class CountryController {

    @Autowired
    private ICountryService iCountryService;
    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("/{id}")
    public CountryOutputDTO getCountryById(@PathVariable String id){
        return this.modelMapper.map(this.iCountryService.getCountryById(id), CountryOutputDTO.class);
    }

    @GetMapping("/country")
    public CountryOutputDTO getCountryByName(@RequestParam(name = "name", defaultValue = "gabon") String name){
        return this.modelMapper.map(this.iCountryService.getCountryByName(name), CountryOutputDTO.class);
    }

    @GetMapping("/country-code/{code}")
    public CountryOutputDTO getCountryByCode(@PathVariable String code){
        return this.modelMapper.map(this.iCountryService.getCountryByCode(code), CountryOutputDTO.class);
    }


    @GetMapping("/country-tenant")
    public CountryOutputDTO getCountry(){
        return this.modelMapper.map(this.iCountryService.getCountryByCode(), CountryOutputDTO.class);
    }



    @DeleteMapping("/{id}")
    public CountryOutputDTO deleteCountryById(@PathVariable String id){
        return convertToOutputDto(this.iCountryService.deleteCountryById(id));
    }


    @GetMapping
    public List<CountryOutputDTO> getAllCountries(){

        return  this.iCountryService.getCountries().stream().map(this::convertToOutputDto).toList();
    }

    @GetMapping("/withoutCities" )
    public List<Country> getAllCountriesWithoutCities(){
        return  this.iCountryService.getCountries().stream().toList();
    }


    @PostMapping
    public CountryOutputDTO addCountry(@RequestBody CountryInputDTO countryInputDTO){
        Country country = modelMapper.map(countryInputDTO, Country.class);
        return convertToOutputDto( this.iCountryService.save(country));
    }


    @PutMapping
    public CountryOutputDTO updateCountry(@RequestBody CountryInputDTO countryInputDTO){
        Country country = modelMapper.map(countryInputDTO, Country.class);
        return convertToOutputDto( this.iCountryService.update(country));
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
