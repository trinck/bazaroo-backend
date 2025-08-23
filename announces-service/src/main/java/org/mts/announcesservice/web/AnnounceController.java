package org.mts.announcesservice.web;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.mts.announcesservice.clients.MediasClient;
import org.mts.announcesservice.clients.StreetClient;
import org.mts.announcesservice.configs.CountryContext;
import org.mts.announcesservice.documents.AnnounceDocument;
import org.mts.announcesservice.dtos.*;
import org.mts.announcesservice.entities.*;
import org.mts.announcesservice.enums.AnnounceStatus;
import org.mts.announcesservice.remote_entities.Media;
import org.mts.announcesservice.remote_entities.Street;
import org.mts.announcesservice.service.*;
import org.mts.announcesservice.utilities.WebUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Slf4j
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
    private final IAuthService iAuthService;
    private final ICategoryService categoryService;

    public AnnounceController(ModelMapper modelMapper, IAnnounceService announceService, IFieldService fieldService, IAnnounceTypeService announceTypeService, MediasClient mediasClient, StreetClient streetClient, IAnnounceSearchService searchService, IAuthService iAuthService, ICategoryService categoryService) {
        this.modelMapper = modelMapper;
        this.announceService = announceService;
        this.fieldService = fieldService;
        this.announceTypeService = announceTypeService;
        this.mediasClient = mediasClient;
        this.streetClient = streetClient;
        this.searchService = searchService;
        this.iAuthService = iAuthService;
        this.categoryService = categoryService;
    }


    @PostMapping("/{category}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public AnnounceOutputDTO create(@PathVariable(required = true) String category, @RequestBody AnnounceInputDTO dto, Authentication authentication) {
        AnnounceType announceType = null;
        Category category1 = null;
        if (dto.getAnnounceTypeId() != null){
            announceType = this.announceTypeService.getByID(dto.getAnnounceTypeId());
            category1 = announceType.getCategory();
        }else {
            category1 = this.categoryService.getByID(category);
        }

        // Initialization of announce entity

        Announce announce = Announce.builder()
                .id(dto.getId())
                .address(dto.getAddress())
                .streetId(dto.getStreetId())
                .imagesLength(dto.getImagesLength())
                .cityId(dto.getCityId())
                .tenantId(CountryContext.getCountry())
                .tel(dto.getTel())
                .price(dto.getPrice())
                .views(0L)
                .clicks(0L)
                .impressions(0L)
                .title(dto.getTitle())
                .location(this.modelMapper.map(dto.getLocation(),GeoZone.class))
                .description(dto.getDescription())
                .postedAt(new Date())
                .status(AnnounceStatus.ACTIVE)
                .userId(authentication.getName())
                .fields(new ArrayList<>())
                .build();
        log.info("Creating advert {} -> {}", announce.getId(),announce );

        announce.setCategory(category1);
        announce.setType(announceType);


       // Mapping of different Field from Json to Entity
        //         Add fields to announce and save
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object o : dto.getObjectFields()) {
            JsonNode node = objectMapper.convertValue(o, JsonNode.class);
            String fieldType = node.get("type").asText("SHORT_TEXT");
            Field targetField = null;
            switch (fieldType) {
                case "TEXT" ->
                        targetField = this.modelMapper.map(objectMapper.convertValue(o, TextInputDTO.class), Text.class);
                case "RADIO" -> {
                    CheckInputDTO checkInputDTO = objectMapper.convertValue(o, CheckInputDTO.class);
                    Radio radio = new Radio();
                    List<CheckUnit> checkUnits = checkUnitsDtoToEntities(checkInputDTO, radio);
                    targetField = radio;

                }
                case "CHECKBOX" -> {
                    CheckInputDTO checkInputDTO = objectMapper.convertValue(o, CheckInputDTO.class);
                    CheckBox box = new CheckBox();
                    List<CheckUnit> checkUnits = checkUnitsDtoToEntities(checkInputDTO, box);
                    targetField = box;
                }
                case "SHORT_TEXT" ->
                        targetField = this.modelMapper.map(objectMapper.convertValue(o, ShortTextInputDTO.class), ShortText.class);
                case "BOOLEAN" ->
                        targetField = this.modelMapper.map(objectMapper.convertValue(o, BooleanInputDTO.class), Bool.class);
                default -> {
                }
            }

            if (targetField != null) announce.addField(targetField);
        }

        return this.modelMapper.map(this.announceService.create(announce), AnnounceOutputDTO.class);
    }


    /********************************************************************
     *@param dto the check(Radio or CheckBox) DTO that contains check unit***
     *@param  check represent Entity target for check dto
     *@return list of checkUnit from list of checkUnitDTO
     * ******************************************************************/
    public List<CheckUnit> checkUnitsDtoToEntities(CheckInputDTO dto, Check check) {
        List<CheckUnit> checkUnits = new ArrayList<>();
        dto.getCheckUnits().forEach(u -> checkUnits.add(CheckUnit.builder().check(check).checked(u.getChecked()).name(u.getName()).dataValue(u.getDataValue()).build()));
        check.setCheckUnits(checkUnits);
        check.setName(dto.getName());
        return checkUnits;
    }


    @GetMapping("/{id}")
    public AnnounceOutputDTO getById(@PathVariable String id) {
        List<Media> medias = this.mediasClient.getAdvertMedias(id);
        Announce announceSource = this.announceService.getByID(id);
        AnnounceOutputDTO announce = this.modelMapper.map(announceSource, AnnounceOutputDTO.class);
        Street street = this.streetClient.getStreet(announceSource.getStreetId());
        announce.setMedias(medias);
        announce.setStreet(street);
        return announce;
    }


    @DeleteMapping("/{id}")
    public AnnounceOutputDTO delete(@PathVariable String id) {
        return this.modelMapper.map(this.announceService.deleteById(id), AnnounceOutputDTO.class);
    }


    @GetMapping
    public Map<String, Object> getAll(@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") int page) {

        Page<Announce> announces = this.announceService.getAnnounces(PageRequest.of(page, size));
        Map<String, Object> map = WebUtils.pageToMap(announces);
        map.put("content", announces.getContent().stream().map(c -> {
            AnnounceOutputDTO ann = this.modelMapper.map(c, AnnounceOutputDTO.class);
            List<Media> medias = this.mediasClient.getAdvertMedias(ann.getId());
            Street street = this.streetClient.getStreet(c.getStreetId());
            ann.setMedias(medias);
            ann.setStreet(street);
            return ann;
        }).toList());
        return map;

    }


    @PostMapping("/in_list_ids")
    public List<AnnounceDocument> getByIds(@RequestBody List<String> ids) {

        List<Announce> announces = this.announceService.getByIds(ids);
        return announces.stream().map(c -> {
            AnnounceDocument ann = this.modelMapper.map(c, AnnounceDocument.class);
            List<Media> medias = this.mediasClient.getAdvertMedias(ann.getId());
            Street street = this.streetClient.getStreet(c.getStreetId());
            ann.setMedias(medias);
            ann.setStreet(street);
            return ann;
        }).toList();

    }


    @GetMapping("/ownAds")
    @PreAuthorize("hasAuthority('USER')")
    public List<AnnounceDocument> getByUserId(@RequestParam(required = false) AnnounceStatus status, Authentication authentication) {

        List<Announce> announces = this.announceService.getAllByUserId(authentication.getName(), status);
        return announces.stream().map(c -> {
            AnnounceDocument ann = this.modelMapper.map(c, AnnounceDocument.class);
            List<Media> medias = this.mediasClient.getAdvertMedias(ann.getId());
            Street street = this.streetClient.getStreet(c.getStreetId());
            ann.setMedias(medias);
            ann.setStreet(street);
            return ann;
        }).toList();

    }

    @PostMapping("/search")
    public Map<String, Object> search(@RequestBody SearchRequestDTO searchRequest) throws IOException {


        searchRequest.setTenantId(CountryContext.getCountry());
        log.info(searchRequest.toString());
        return this.searchService.searchAnnounces(searchRequest);

    }


}
