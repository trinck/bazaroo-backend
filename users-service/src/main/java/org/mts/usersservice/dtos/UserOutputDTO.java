package org.mts.usersservice.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.usersservice.entities.Auth;
import org.mts.usersservice.enums.Gender;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
public class UserOutputDTO {

    protected String id;
    protected String firstname;
    protected String lastname;
    protected Date birthday;
    protected String address;
    protected String avatar;
    protected String email;
    protected Gender gender;
    protected Boolean active;
    protected Boolean verified;
    protected String profileUrl;
    protected AuthOutputDTO auth;
    protected String cityId;
    protected List<ContactOutputDTO> contacts = new ArrayList<>();
}
