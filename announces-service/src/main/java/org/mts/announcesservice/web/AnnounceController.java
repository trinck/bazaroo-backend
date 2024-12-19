package org.mts.announcesservice.web;


import co.elastic.clients.elasticsearch._types.SortOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.mts.announcesservice.clients.MediasClient;
import org.mts.announcesservice.clients.StreetClient;
import org.mts.announcesservice.dtos.*;
import org.mts.announcesservice.entities.*;
import org.mts.announcesservice.enums.AnnounceStatus;
import org.mts.announcesservice.remote_entities.Media;
import org.mts.announcesservice.remote_entities.Street;
import org.mts.announcesservice.service.*;
import org.mts.announcesservice.utilities.WebUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("announces")
public class AnnounceController {

    private final ModelMapper modelMapper;
    private final IAnnounceService announceService;
    private final IFieldService fieldService;
    private final IAnnounceTypeService announceTypeService;
    private final MediasClient mediasClient;
    private final StreetClient streetClient;
    private final IAnnounceSearchService searchService;

    public AnnounceController(ModelMapper modelMapper, IAnnounceService announceService, IFieldService fieldService, IAnnounceTypeService announceTypeService, MediasClient mediasClient, StreetClient streetClient, IAnnounceSearchService searchService) {
        this.modelMapper = modelMapper;
        this.announceService = announceService;
        this.fieldService = fieldService;
        this.announceTypeService = announceTypeService;
        this.mediasClient = mediasClient;
        this.streetClient = streetClient;
        this.searchService = searchService;
    }


    @PostMapping("/{type}")
    public AnnounceOutputDTO create(@PathVariable String type, @RequestBody AnnounceInputDTO dto){
        AnnounceType announceType = this.announceTypeService.getByID(type);
        /***
         * *************************************
         * * Initialization of announce entity**
         * *************************************/

        Announce announce = Announce.builder()
                .address(dto.getAddress())
                .streetId(dto.getStreetId())
                .tel(dto.getTel())
                .price(dto.getPrice())
                .title(dto.getTitle())
                .description(dto.getDescription())
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
            String fieldType = node.get("type").asText("SHORT_TEXT");
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
    public AnnounceOutputDTO getById(@PathVariable String id){
        List<Media> medias = this.mediasClient.getAdvertMedias(id);
        Announce announceSource = this.announceService.getByID(id);
        AnnounceOutputDTO announce = this.modelMapper.map(announceSource, AnnounceOutputDTO.class);
        Street street = this.streetClient.getStreet(announceSource.getStreetId());
        announce.setMedias(medias);
        announce.setStreet(street);
        return  announce;
    }


    @DeleteMapping("/{id}")
    public AnnounceOutputDTO delete(@PathVariable String id){
        return this.modelMapper.map(this.announceService.deleteById(id), AnnounceOutputDTO.class);
    }


    @GetMapping
    public Map<String, Object> getAll(@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") int page){

        Page<Announce> announces = this.announceService.getAnnounces(PageRequest.of(page, size));
        Map<String, Object> map = WebUtils.pageToMap(announces);
        map.put("content", announces.getContent().stream().map(c-> {
            AnnounceOutputDTO ann = this.modelMapper.map(c, AnnounceOutputDTO.class);
            List<Media> medias = this.mediasClient.getAdvertMedias(ann.getId());
            Street street = this.streetClient.getStreet(c.getStreetId());
            ann.setMedias(medias);
            ann.setStreet(street);
            return ann;
        }).toList());
        return map;

    }
@GetMapping("/search")
    public Map<String, Object> search(@RequestParam(required = false) String query,
                                      @RequestParam(required = false) String category,
                                      @RequestParam(required = false) String sortBy,
                                      @RequestParam(defaultValue = "ASC") String order,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) throws IOException {


        SortOrder sortOrder = "ASC".equalsIgnoreCase(order) ? SortOrder.Asc : SortOrder.Desc;
        return searchService.searchAnnounces(query, category, sortBy, sortOrder, page, size);

    }




}
