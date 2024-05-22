package org.mts.usersservice.web;


import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.mts.usersservice.dtos.PreferenceInputDTO;
import org.mts.usersservice.dtos.PreferenceOutputDTO;
import org.mts.usersservice.entities.Preference;
import org.mts.usersservice.services.IPreferenceService;
import org.mts.usersservice.services.IUserService;
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
    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public PreferenceOutputDTO getPreference(@PathVariable @NotNull Long id){
       return this.modelMapper.map(this.preferenceService.getPreferenceById(id), PreferenceOutputDTO.class);
    }

    @PutMapping("/{id}")
    public PreferenceOutputDTO updatePreference(@PathVariable @NotNull Long id, @RequestBody PreferenceInputDTO dto){
        Preference preference = this.modelMapper.map(dto, Preference.class);
        return this.modelMapper.map(this.preferenceService.updatePreference(preference), PreferenceOutputDTO.class);
    }

    @DeleteMapping("/{id}")
    public PreferenceOutputDTO deletePreference(@PathVariable @NotNull Long id){
     return  this.modelMapper.map(this.preferenceService.deletePreferenceById(id), PreferenceOutputDTO.class);
    }


    @GetMapping("/")
    public Map<String,Object> getPreferences(@RequestParam(defaultValue = "8") int size, @RequestParam(defaultValue = "0") int page){

        Page<Preference> preferences = this.preferenceService.getPreferences(PageRequest.of(page,size));
        Map<String,Object> dtos = WebUtils.pageToMap(preferences);
        dtos.put("content", preferences.getContent().stream().map(c -> this.modelMapper.map(c, PreferenceOutputDTO.class)).toList());
        return dtos;
    }
}
