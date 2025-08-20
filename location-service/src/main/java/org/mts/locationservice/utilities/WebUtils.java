package org.mts.locationservice.utilities;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class WebUtils {

    public static Map<String, Object> pageToMap(Page<?> page){
        Map<String,Object> map = new HashMap<>();
        map.put("totalElements", page.getTotalElements());
        map.put("page",page.getNumber());
        map.put("totalPages", page.getTotalPages());
        map.put("size", page.getSize());
        map.put("number", page.getNumber());
        map.put("numberOfElements", page.getNumberOfElements());
        map.put("pageable", page.getPageable());
        return  map;
    }
}
