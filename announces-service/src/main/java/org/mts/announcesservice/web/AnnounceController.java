package org.mts.announcesservice.web;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.mts.announcesservice.clients.MediasClient;
import org.mts.announcesservice.dtos.*;
import org.mts.announcesservice.entities.*;
import org.mts.announcesservice.enums.AnnounceStatus;
import org.mts.announcesservice.remote_entities.Media;
import org.mts.announcesservice.service.IAnnounceService;
import org.mts.announcesservice.service.IAnnounceTypeService;
import org.mts.announcesservice.service.IFieldService;
import org.mts.announcesservice.utilities.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("announces")
public class AnnounceController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IAnnounceService announceService;
    @Autowired
    private IFieldService fieldService;
    @Autowired
    private IAnnounceTypeService announceTypeService;
    @Autowired
    private MediasClient mediasClient;



    @PostMapping("/{type}")
    public AnnounceOutputDTO create(@PathVariable String type, @RequestBody AnnounceInputDTO dto){
        AnnounceType announceType = this.announceTypeService.getByID(type);
        /***
         * *************************************
         * * Initialization of announce entity**
         * *************************************/

        Announce announce = Announce.builder()
                .address(dto.getAddress())
                .cityId(dto.getCityId())
                .price(dto.getPrice())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .locationId(dto.getLocationId())
                .postedAt(new Date())
                .status(AnnounceStatus.ACTIVE)
                .userId(UUID.randomUUID().toString())
                .fields(new ArrayList<>())
                .build();

        announce.setCategory(announceType.getCategory());
        announce.setType(announceType);


        /******************************************************
        ** ****Mapping of different Field from Json to Entity **
         *********Add fields to announce and save**************
        * ******************************************************/
        ObjectMapper objectMapper = new ObjectMapper();
        for(Object o : dto.getObjectFields()){
            JsonNode node = objectMapper.convertValue(o, JsonNode.class);
            String fieldType = node.get("fieldType").asText("SHORT_TEXT");
            Field targetField = null;
            switch (fieldType){
                case "TEXT" -> targetField = this.modelMapper.map(objectMapper.convertValue(o, TextInputDTO.class), Text.class);
                case "RADIO" -> {
                    CheckInputDTO checkInputDTO = objectMapper.convertValue(o, CheckInputDTO.class);
                    Radio radio = new Radio();
                    List<CheckUnit> checkUnits = checkUnitsDtoToEntities(checkInputDTO,radio);
                    targetField = radio;

                }
                case "CHECKBOX" -> {
                    CheckInputDTO checkInputDTO = objectMapper.convertValue(o, CheckInputDTO.class);
                    CheckBox box = new CheckBox();
                    List<CheckUnit> checkUnits = checkUnitsDtoToEntities(checkInputDTO,box);
                    targetField = box;
                }
                case "SHORT_TEXT" -> targetField = this.modelMapper.map(objectMapper.convertValue(o, ShortTextInputDTO.class), ShortText.class);
                case "BOOLEAN" -> targetField = this.modelMapper.map(objectMapper.convertValue(o, BooleanInputDTO.class), Bool.class);
                default ->{}
            }

            if(targetField!=null)announce.addField(targetField);
        }
        return this.modelMapper.map(this.announceService.create(announce), AnnounceOutputDTO.class);
    }



    /********************************************************************
     *@param dto the check(Radio or CheckBox) DTO that contains check unit***
     *@param  check represent Entity target for check dto
     *@return list of checkUnit from list of checkUnitDTO
     * ******************************************************************/
    public List<CheckUnit> checkUnitsDtoToEntities(CheckInputDTO dto, Check check){
        List<CheckUnit> checkUnits = new ArrayList<>();
        dto.getCheckUnits().forEach(u-> checkUnits.add(CheckUnit.builder().check(check).checked(u.getChecked()).name(u.getName()).dataValue(u.getDataValue()).build()));
        check.setCheckUnits(checkUnits);
        check.setName(dto.getName());
        return checkUnits;
    }


    @GetMapping("/{id}")
    public AnnounceWithMedias getById(@PathVariable String id){
        List<Media> medias = this.mediasClient.getAdvertMedias(id);
        return  new AnnounceWithMedias(this.modelMapper.map(this.announceService.getByID(id), AnnounceOutputDTO.class),medias);
    }


    @DeleteMapping("/{id}")
    public AnnounceOutputDTO delete(@PathVariable String id){
        return this.modelMapper.map(this.announceService.deleteById(id), AnnounceOutputDTO.class);
    }


    @GetMapping
    public Map<String, Object> getAll(@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") int page){

        Page<Announce> announces = this.announceService.getAnnounces(PageRequest.of(page, size));
        Map<String, Object> map = WebUtils.pageToMap(announces);
        map.put("content", announces.getContent().stream().map(c->this.modelMapper.map(c, AnnounceOutputDTO.class)).toList());
        return map;

    }




}
