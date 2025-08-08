package org.mts.usersservice.web;


import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.mts.usersservice.entities.Preference;
import org.mts.usersservice.services.IPreferenceService;
import org.mts.usersservice.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("preferences")
public class PreferenceController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IPreferenceService preferenceService;

    @GetMapping("/{userId}")
    public Preference getPreference(@PathVariable @NotNull String userId){
       return this.preferenceService.getPreferenceByUserId(userId) ;
    }

    @PutMapping
    public Preference updatePreference( @RequestBody Preference preference){
        return this.preferenceService.updatePreference(preference);
    }

    @DeleteMapping("/{userId}")
    public Preference deletePreference(@PathVariable @NotNull String userId){
     return  this.preferenceService.deletePreferenceByUserId(userId);
    }


    @GetMapping
    public Map<String,Object> getPreferences(@RequestParam(defaultValue = "8") int size, @RequestParam(defaultValue = "0") int page){

        Page<Preference> preferences = this.preferenceService.getPreferences(PageRequest.of(page,size));
        Map<String,Object> dtos = WebUtils.pageToMap(preferences);
        dtos.put("content", preferences.getContent());
        return dtos;
    }
}
