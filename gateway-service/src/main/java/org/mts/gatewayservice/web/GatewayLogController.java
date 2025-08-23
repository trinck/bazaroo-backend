package org.mts.gatewayservice.web;

import org.mts.gatewayservice.entities.GatewayLog;
import org.mts.gatewayservice.service.IGatewayLogService;
import org.mts.gatewayservice.utilities.WebUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/gateway_logs")
public class GatewayLogController {

    private final IGatewayLogService gatewayLogService;

    public GatewayLogController(IGatewayLogService gatewayLogService) {
        this.gatewayLogService = gatewayLogService;
    }

    @DeleteMapping("/{id}")
    public GatewayLog delete(@PathVariable("id") Long id){
        return this.gatewayLogService.deleteById(id);
    }


    @GetMapping
    public Map<String, Object> getAll(
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String sortField,
            @RequestParam(defaultValue = "desc") String sortOrder,
            @RequestParam(required = false) String search) {

        Sort sort = Sort.unsorted();
        if (sortField != null && !sortField.isBlank()) {
            sort = "desc".equalsIgnoreCase(sortOrder)
                    ? Sort.by(sortField).descending()
                    : Sort.by(sortField).ascending();
        }
        Page<GatewayLog> logs = this.gatewayLogService.getAll(PageRequest.of(page, size, sort), search);
        Map<String, Object> map = WebUtils.pageToMap(logs);
        map.put("content", logs.getContent().stream().toList());
        return map;
    }
}
