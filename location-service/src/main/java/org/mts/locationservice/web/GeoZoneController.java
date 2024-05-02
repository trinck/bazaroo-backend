package org.mts.locationservice.web;

import jakarta.ws.rs.NotFoundException;
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
    @GetMapping("/index/{id}")
    public GeoZoneOutputDTO getGeoZoneById(@PathVariable Long id){

        GeoZone geoZone = this.iGeoZoneService.getZoneById(id);

        if(geoZone == null){
            throw new NotFoundException("Zone with id:"+id+" doesn't exists");
        }

        return convertToOutputDto(geoZone);
    }

    @GetMapping("/delete/{id}")
    public GeoZoneOutputDTO deleteGeoZoneById(@PathVariable Long id){

        GeoZone zone = this.iGeoZoneService.deleteZoneById(id);

        if(zone == null){
            throw new NotFoundException("Zone with id:"+id+" doesn't exists");
        }
        return convertToOutputDto(zone);
    }


    @GetMapping("/geoZones")
    public List<GeoZoneOutputDTO> getGeoZones(){
        List<GeoZone> geoZones = this.iGeoZoneService.getZones();
        return  geoZones.stream().map(this::convertToOutputDto).toList();

    }


    @PostMapping("/save")
    public GeoZoneOutputDTO addGeoZone(@RequestBody GeoZoneInputDTO geoZoneInputDTO){

        GeoZone zone = convertToEntity(geoZoneInputDTO);
        zone = this.iGeoZoneService.save(zone);

        return convertToOutputDto(zone);
    }


    private GeoZoneOutputDTO convertToOutputDto(GeoZone geoZone) {
        return modelMapper.map(geoZone, GeoZoneOutputDTO.class);
    }

    private GeoZoneInputDTO convertToInputDto(GeoZone geoZone) {
        return modelMapper.map(geoZone, GeoZoneInputDTO.class);
    }

    private GeoZone convertToEntity(GeoZoneInputDTO geoZoneInputDTO) {
        return modelMapper.map(geoZoneInputDTO, GeoZone.class);
    }

    private GeoZone convertToEntity(GeoZoneOutputDTO geoZoneOutputDTO) {
        return modelMapper.map(geoZoneOutputDTO, GeoZone.class);
    }



}
