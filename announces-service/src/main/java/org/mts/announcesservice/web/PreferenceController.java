package org.mts.announcesservice.web;


import lombok.extern.slf4j.Slf4j;
import org.mts.announcesservice.entities.Preference;
import org.mts.announcesservice.service.IPreferenceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("preferences")
@RestController
public class PreferenceController {


    private final IPreferenceService preferenceService;

    public PreferenceController(IPreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping
    public Preference updatePreference(@RequestBody Preference preference) {
        return  this.preferenceService.save(preference);
    }

    @GetMapping("/{userId}")
    public Preference getPreferenceByUserId(@PathVariable String userId) {
        return  this.preferenceService.getPreferenceByUserId(userId);
    }


}
