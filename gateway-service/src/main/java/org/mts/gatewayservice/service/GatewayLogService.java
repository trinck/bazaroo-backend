package org.mts.gatewayservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mts.gatewayservice.entities.GatewayLog;
import org.mts.gatewayservice.repositories.GatewayLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class GatewayLogService implements IGatewayLogService {

    private final GatewayLogRepository gatewayLogRepository;
    private final ObjectMapper mapper;

    public GatewayLogService(GatewayLogRepository gatewayLogRepository,  ObjectMapper mapper) {
        this.gatewayLogRepository = gatewayLogRepository;
        this.mapper = mapper;
    }

    /**
     * @param gatewayLog
     * @return
     */
    @Override
    @Async("loggingExecutor")
    public void createAsync(GatewayLog gatewayLog) {
        this.gatewayLogRepository.save(gatewayLog);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public GatewayLog deleteById(Long id) {
        GatewayLog gatewayLog = this.gatewayLogRepository.findById(id).orElseThrow();
        this.gatewayLogRepository.deleteById(id);
        return gatewayLog;
    }

    /**
     * @param pageable
     * @param search
     * @return
     */
    @Override
    public Page<GatewayLog> getAll(Pageable pageable, String search) {
        if (!StringUtils.hasText(search)) {
            return gatewayLogRepository.findAll(pageable); // no spec needed
        }
        Specification<GatewayLog> spec = buildSearchSpec(search);
        return this.gatewayLogRepository.findAll(spec, pageable);
    }

    public String toJson(Map<String, ?> map) {
        try { return mapper.writeValueAsString(map); }
        catch (Exception e) { return "{}"; }
    }

    public Map<String, String> maskHeaders(Map<String, String> in) {
        if (in == null) return Map.of();
        return in.entrySet().stream().collect(java.util.stream.Collectors.toMap(
                Map.Entry::getKey,
                e -> switch (e.getKey().toLowerCase()) {
                    case "authorization", "cookie", "set-cookie" -> "***";
                    default -> e.getValue();
                }
        ));
    }

    private Specification<GatewayLog> buildSearchSpec(String search) {
        if (!StringUtils.hasText(search)) return null; // no filter -> all

        final String like = "%" + search.toLowerCase() + "%";

        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("remoteAddr")), like),
                cb.like(cb.lower(root.get("userAgent")), like),
                cb.like(cb.lower(root.get("method")), like),
                cb.like(cb.lower(root.get("path")), like),
                cb.like(cb.lower(root.get("targetServiceId")), like)
        );
    }

}
