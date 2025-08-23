package org.mts.gatewayservice.service;

import org.mts.gatewayservice.entities.GatewayLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface IGatewayLogService {
    public void createAsync(GatewayLog gatewayLog);
    public GatewayLog deleteById(Long id);
    public Page<GatewayLog> getAll(Pageable pageable, String search);
    public String toJson(Map<String, ?> map);
    public Map<String, String> maskHeaders(Map<String, String> in);
}
