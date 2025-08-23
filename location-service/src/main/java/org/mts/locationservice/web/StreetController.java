package org.mts.locationservice.web;


import org.modelmapper.ModelMapper;
import org.mts.locationservice.dtos.StreetInputDTO;
import org.mts.locationservice.dtos.StreetOutputDTO;
import org.mts.locationservice.entities.City;
import org.mts.locationservice.entities.Street;
import org.mts.locationservice.services.ICityService;
import org.mts.locationservice.services.IStreetService;
import org.mts.locationservice.utilities.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/list")
    public Map<String, Object> getList(
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) String search) {

        Sort sort = Sort.unsorted();
        if (sortField != null && !sortField.isBlank()) {
            sort = "desc".equalsIgnoreCase(sortOrder)
                    ? Sort.by(sortField).descending()
                    : Sort.by(sortField).ascending();
        }

        Page<Street> streets = this.StreetService.getStreetsPages(PageRequest.of(page, size, sort), search);
        Map<String, Object> map = WebUtils.pageToMap(streets);
        map.put("content", streets.getContent().stream().toList());
        return map;
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
