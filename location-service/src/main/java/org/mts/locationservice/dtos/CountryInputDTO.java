package org.mts.locationservice.dtos;



import lombok.*;


@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class CountryInputDTO {

    private String id;
    private String name;
    private String code;
    private String telCode;
    private String currency;
    private String currencyName;
}
