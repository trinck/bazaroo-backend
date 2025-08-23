package org.mts.gatewayservice.filters;


import org.mts.gatewayservice.entities.GatewayLog;
import org.mts.gatewayservice.service.IGatewayLogService;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final int MAX_BODY = 16_384; // truncate big bodies
    private static final Set<String> LOG_METHODS = Set.of("POST", "PUT", "DELETE");
    private final IGatewayLogService logService;


    public CustomGlobalFilter(IGatewayLogService logService) {
        this.logService = logService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        long startNanos = System.nanoTime();

        ServerHttpRequest req = exchange.getRequest();
        HttpMethod method = req.getMethod();
        String methodValue = (method != null ? method.name() : null);

        // Only log for POST, PUT, DELETE
        if (LOG_METHODS.contains(methodValue.toUpperCase())) {

            String remoteAddr = Optional.ofNullable(exchange.getRequest().getRemoteAddress())
                    .map(a -> a.getAddress().getHostAddress()).orElse("unknown");

            // capture the request body if you need it (only for small JSON/text)
            AtomicReference<String> reqBodyRef = new AtomicReference<>("");
            // body capture for request requires a custom decorator if you forward it downstream.
            // Wrap request to capture body
            Flux<DataBuffer> cachedRequestBody = DataBufferUtils.join(req.getBody())
                    .map(db -> {
                        byte[] bytes = new byte[db.readableByteCount()];
                        db.read(bytes);
                        DataBufferUtils.release(db);
                        String body = new String(bytes, StandardCharsets.UTF_8);
                        if (body.length() > MAX_BODY) body = body.substring(0, MAX_BODY) + "...(truncated)";
                        reqBodyRef.set(body);
                        return exchange.getResponse().bufferFactory().wrap(bytes);
                    }).flux();

            ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(req) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return cachedRequestBody;
                }
            };

            // Wrap response to capture body
            ServerHttpResponse originalResponse = exchange.getResponse();
            ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    if (body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                        return super.writeWith(fluxBody.map(dataBuffer -> {
                            byte[] content = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(content);
                            DataBufferUtils.release(dataBuffer);
                            String respBody = new String(content, StandardCharsets.UTF_8);
                            if (respBody.length() > MAX_BODY) respBody = respBody.substring(0, MAX_BODY) + "...(truncated)";
                            exchange.getAttributes().put("respBody", respBody);
                            return bufferFactory().wrap(content);
                        }));
                    }
                    return super.writeWith(body);
                }
            };

            // Proceed downstream and capture status/response
            return chain.filter(exchange.mutate()
                            .request(mutatedRequest)
                            .response(decoratedResponse)
                            .build())
                    .then(Mono.fromRunnable(() -> {
                            GatewayLog log = new GatewayLog();
                            log.setRemoteAddr(remoteAddr);
                            log.setUserAgent(req.getHeaders().getFirst(HttpHeaders.USER_AGENT));
                            log.setMethod(methodValue);
                            log.setPath(req.getURI().getRawPath());

                            // Target route/service id
                            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
                            String serviceId = "unknown";
                            if (route != null) {
                                String id = route.getId();
                                // Remove the DiscoveryClient prefix if present
                                if (id.startsWith("ReactiveCompositeDiscoveryClient_")) {
                                    serviceId = id.replace("ReactiveCompositeDiscoveryClient_", "");
                                } else {
                                    serviceId = id;
                                }
                            }
                            log.setTargetServiceId(serviceId);

                            // Query params
                            Map<String, String> qp = new LinkedHashMap<>();
                            req.getQueryParams().forEach((k, v) -> qp.put(k, String.join(",", v)));
                            log.setQueryParamsJson(logService.toJson(qp));

                            // Headers (masked)
                            Map<String, String> headers = req.getHeaders().entrySet().stream()
                                    .collect(Collectors.toMap(
                                            Map.Entry::getKey,
                                            e -> String.join(",", e.getValue()),
                                            (v1, v2) -> v1,  // merge function, if duplicate keys
                                            LinkedHashMap::new
                                    ));
                            headers = logService.maskHeaders(headers);
                            log.setHeadersJson(logService.toJson(headers));

                            // Request body (if captured elsewhere)
                            String reqBody = reqBodyRef.get();
                            if (reqBody.length() > MAX_BODY) reqBody = reqBody.substring(0, MAX_BODY) + "...(truncated)";
                            log.setRequestBody(reqBody);

                            // Response info
                            int status = exchange.getResponse().getStatusCode() != null
                                    ? exchange.getResponse().getStatusCode().value()
                                    : 0;
                            if (exchange.getResponse().getStatusCode() != null) {
                                status = exchange.getResponse().getStatusCode().value();
                            }
                            log.setResponseStatus(status);

                            log.setResponseBody((String) exchange.getAttribute("respBody"));

                            long durationMs = (System.nanoTime() - startNanos) / 1_000_000;
                            log.setDurationMs(durationMs);

                            logService.createAsync(log); // offload to JPA async
                    }));
        }

        return chain.filter(exchange);

    }



    @Override
    public int getOrder() {
        return -1; // Plus haut niveau pour intervenir après la réponse
    }
}
