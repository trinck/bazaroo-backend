package org.mts.usersservice.configs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "app.client.preference")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaultClientPref {

    private Boolean nightTheme;
    private String language;
    private Boolean telVisible;
    private Boolean whatsappVisible;
}
