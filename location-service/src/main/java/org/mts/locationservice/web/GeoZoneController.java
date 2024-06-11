package org.mts.locationservice.web;

import org.modelmapper.ModelMapper;
import org.mts.locationservice.dtos.GeoZoneInputDTO;
import org.mts.locationservice.dtos.GeoZoneOutputDTO;
import org.mts.locationservice.entities.GeoZone;
import org.mts.locationservice.services.IGeoZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("geoZones")
public class GeoZoneController {


    @Autowired
    private IGeoZoneService iGeoZoneService;

    @Autowired
    private ModelMapper modelMapper;
    @GetMapping("/{id}")
    public GeoZoneOutputDTO getGeoZoneById(@PathVariable String id){
        return convertToOutputDto(this.iGeoZoneService.getZoneById(id));
    }

    @DeleteMapping("/{id}")
    public GeoZoneOutputDTO deleteGeoZoneById(@PathVariable String id){
        return convertToOutputDto(this.iGeoZoneService.deleteZoneById(id));
    }


    @GetMapping
    public List<GeoZoneOutputDTO> getGeoZones(){
        return  this.iGeoZoneService.getZones().stream().map(this::convertToOutputDto).toList();
    }


    @PostMapping
    public GeoZoneOutputDTO addGeoZone(@RequestBody GeoZoneInputDTO geoZoneInputDTO){

        GeoZone zone = convertToEntity(geoZoneInputDTO);
        return convertToOutputDto(this.iGeoZoneService.save(zone));
    }

    @PutMapping("/{id}")
    public GeoZoneOutputDTO addGeoZone(@PathVariable String id,@RequestBody GeoZoneInputDTO geoZoneInputDTO){
        GeoZone zone = convertToEntity(geoZoneInputDTO);
        return convertToOutputDto(this.iGeoZoneService.update(zone));
    }


    private GeoZoneOutputDTO convertToOutputDto(GeoZone geoZone) {
        return modelMapper.map(geoZone, GeoZoneOutputDTO.class);
    }


    private GeoZone convertToEntity(GeoZoneInputDTO geoZoneInputDTO) {
        return modelMapper.map(geoZoneInputDTO, GeoZone.class);
    }


}
