package org.mts.usersservice.dtos;


import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientInputDTO extends UserInputDTO{

    private PreferenceInputDTO preference;
    private String whatsapp;
}
