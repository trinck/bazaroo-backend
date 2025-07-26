package org.mts.notificationservice.configs;


import lombok.extern.slf4j.Slf4j;
import org.mts.notificationservice.ws.UserHandshakeHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {


    private final JwtDecoder jwtDecoder;
    private final JwtAuthConverter jwtAuthConverter;

    public WebsocketConfig( JwtDecoder jwtDecoder, JwtAuthConverter jwtAuthConverter) {
        this.jwtDecoder = jwtDecoder;
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setHandshakeHandler(new UserHandshakeHandler(jwtDecoder)).setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Préfixe des destinations côté client pour les messages envoyés au serveur (ex: /app/...)
        registry.setApplicationDestinationPrefixes("/app");

        // Enable a simple memory-based message broker to carry the messages back to the client
        registry.enableSimpleBroker("/topic", "/queue");

        //  pour envoyer à des utilisateurs spécifiques avec convertAndSendToUser
        registry.setUserDestinationPrefix("/user");
    }





    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> authHeader = accessor.getNativeHeader("Authorization");
                    if (authHeader != null && !authHeader.isEmpty()) {
                        String bearer = authHeader.get(0);
                        if (bearer.startsWith("Bearer ")) {
                            String token = bearer.substring(7);
                            try {
                                Jwt jwt = jwtDecoder.decode(token);
                                AbstractAuthenticationToken authentication = jwtAuthConverter.convert(jwt);
                                if (authentication!=null){authentication.setAuthenticated(true);}
                                SecurityContextHolder.getContext().setAuthentication(authentication);
                                accessor.setUser(authentication);

                            } catch (JwtException e) {
                                throw new IllegalArgumentException("Invalid JWT token", e);
                            }
                        }
                    }
                }
                return message;
            }
        });
    }
}
