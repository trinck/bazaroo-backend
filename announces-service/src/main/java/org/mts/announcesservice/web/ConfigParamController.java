package org.mts.announcesservice.web;


import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("configs")
public class ConfigParamController {

    @Value("${spring.application.name}")
    private String name;


    @GetMapping
    public List<Object> getParams(){
        return List.of(name) ;
    }
}
