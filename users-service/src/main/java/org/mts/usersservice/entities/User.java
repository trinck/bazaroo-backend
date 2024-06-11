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
    protected String id;
    protected String firstname;
    protected String lastname;
    @Column(unique = true)
    protected String email;
    protected Date birthday;
    protected String address;
    protected String avatar;

    @Enumerated(EnumType.STRING)
    protected Gender gender;
    protected Boolean active;
    protected Boolean verified;
    protected String profileUrl;

    @OneToOne(orphanRemoval = true)
    protected Auth auth;
    protected String cityId;

    @OneToMany(mappedBy = "user")
    protected List<Contact> contacts = new ArrayList<>();

}
