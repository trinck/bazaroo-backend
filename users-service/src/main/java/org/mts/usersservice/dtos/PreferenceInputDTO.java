package org.mts.usersservice.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreferenceInputDTO {

    private Long id;
    private Boolean NightTheme;
    private String Language;
    private Boolean telVisible;
    private Boolean whatsappVisible;

}
