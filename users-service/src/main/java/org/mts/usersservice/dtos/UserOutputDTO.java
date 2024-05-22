package org.mts.usersservice.dtos;

import lombok.Data;
import org.mts.usersservice.entities.Auth;
import org.mts.usersservice.enums.Gender;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
public class UserOutputDTO {

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
    private AuthOutputDTO auth;
    private String cityId;
    private List<ContactOutputDTO> contacts = new ArrayList<>();
}
