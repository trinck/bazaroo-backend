package org.mts.usersservice.dtos;

import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientOutputDTO extends UserOutputDTO{

    private PreferenceOutputDTO preference;
    private String whatsapp;
}
