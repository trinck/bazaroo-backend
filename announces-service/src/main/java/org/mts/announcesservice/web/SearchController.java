package org.mts.announcesservice.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.mts.announcesservice.clients.MediasClient;
import org.mts.announcesservice.clients.StreetClient;
import org.mts.announcesservice.configs.CountryContext;
import org.mts.announcesservice.dtos.SavedSearchInputDto;
import org.mts.announcesservice.dtos.SavedSearchRequest;
import org.mts.announcesservice.entities.SavedSearch;
import org.mts.announcesservice.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("search")
public class SearchController {

    private final ModelMapper modelMapper;
    private final IAnnounceService announceService;
    private final IAnnounceSearchService searchService;

    public SearchController(ModelMapper modelMapper, IAnnounceService announceService, IFieldService fieldService, IAnnounceTypeService announceTypeService, MediasClient mediasClient, StreetClient streetClient, IAnnounceSearchService searchService, IAuthService iAuthService) {
        this.modelMapper = modelMapper;
        this.announceService = announceService;
        this.searchService = searchService;
    }

//    @PostMapping
//    @PreAuthorize("hasAnyAuthority('USER')")
//    public ResponseEntity<String> saveSearch(@RequestBody SavedSearchInputDto searchInputDto){
//
//        this.searchService.saveSearch(searchInputDto);
//        return ResponseEntity.ok("Search saved with success");
//    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<String> saveSearch(@RequestBody SavedSearchRequest searchRequest) {

        searchRequest.getCriteria().setTenantId(CountryContext.getCountry());
        this.searchService.saveSearch(searchRequest);
        return ResponseEntity.ok("Search saved with success");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<String> updateSaveSearch(@RequestBody SavedSearchInputDto searchRequest, @PathVariable Long id, Authentication authentication) {

        SavedSearch savedSearch = this.searchService.getSavedSearchByIdAndUserId(id, authentication.getName());
        savedSearch.setSearchName(searchRequest.getSearchName());
        savedSearch.setActive(searchRequest.getActive());
        savedSearch.setTime(searchRequest.getTime());
        try {
            savedSearch.setCriteria(new ObjectMapper().writeValueAsString(searchRequest.getCriteria()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        savedSearch.setType(searchRequest.getType());

        this.searchService.update(savedSearch);
        return ResponseEntity.ok("Search updated with success");
    }
}
