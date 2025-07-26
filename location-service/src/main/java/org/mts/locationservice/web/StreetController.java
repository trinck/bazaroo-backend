package org.mts.locationservice.web;


import org.modelmapper.ModelMapper;
import org.mts.locationservice.dtos.StreetInputDTO;
import org.mts.locationservice.dtos.StreetOutputDTO;
import org.mts.locationservice.entities.City;
import org.mts.locationservice.entities.Street;
import org.mts.locationservice.services.ICityService;
import org.mts.locationservice.services.IStreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("streets")
public class StreetController {

    @Autowired
    private IStreetService StreetService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ICityService iCityService;


    @GetMapping("/{id}")
    public StreetOutputDTO getStreetById(@PathVariable String id){
        return this.modelMapper.map(this.StreetService.getStreetById(id), StreetOutputDTO.class);
    }

    @DeleteMapping("/{id}")
    public StreetOutputDTO deleteStreetById(@PathVariable String id){
        return this.modelMapper.map(this.StreetService.deleteStreetById(id), StreetOutputDTO.class);
    }


    @GetMapping
    public List<StreetOutputDTO> getAllStreets(){
        return  this.StreetService.getStreets().stream().map(this::convertToOutputDto).toList();
    }

    @GetMapping("/withCity")
    public List<Street> getAllStreetsWithCity(){
        return  this.StreetService.getStreets().stream().toList();
    }


    @PostMapping("/{cityId}")
    public StreetOutputDTO saveStreet(@PathVariable(required = true) String cityId,@RequestBody StreetInputDTO streetInputDTO){

        Street street = this.modelMapper.map(streetInputDTO, Street.class);
        City city = this.iCityService.getCityById(cityId);
        street.setCity(city);
        return this.modelMapper.map(this.StreetService.saveStreet(street), StreetOutputDTO.class);
    }




    private StreetOutputDTO convertToOutputDto(Street street) {
        return modelMapper.map(street, StreetOutputDTO.class);
    }

}
