package org.mts.usersservice.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.usersservice.enums.Gender;

import java.util.Date;

@Data
public abstract class UserInputDTO {

    protected String id;
    protected String firstname;
    protected String lastname;
    protected Date birthday;
    protected String address;
    protected String avatar;
    protected Gender gender;
    protected Boolean active;
    protected Boolean verified;
    protected String email;
    protected String profileUrl;
    protected AuthInputDTO auth;
    protected String cityId;



}
