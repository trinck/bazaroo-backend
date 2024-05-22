package org.mts.usersservice.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.usersservice.enums.Gender;

import java.util.Date;

@Data
public abstract class UserInputDTO {

    private String id;
    private String firstname;
    private String lastname;
    private Date birthday;
    private String address;
    private String avatar;
    private Gender gender;
    private Boolean active;
    private Boolean verified;
    private String profileUrl;
    private AuthInputDTO auth;
    private String cityId;



}
