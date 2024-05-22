package org.mts.usersservice.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Client extends  User{


    @OneToOne(cascade = CascadeType.PERSIST)
    private Preference preference;
    private String whatsapp;
}
