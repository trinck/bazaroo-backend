package org.mts.usersservice.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.usersservice.enums.Gender;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String firstname;
    private String lastname;
    private Date birthday;
    private String address;
    private String avatar;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Boolean active;
    private Boolean verified;
    private String profileUrl;

    @OneToOne(orphanRemoval = true)
    private Auth auth;
    private String cityId;

    @OneToMany(mappedBy = "user")
    private List<Contact> contacts = new ArrayList<>();

}
