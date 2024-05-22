package org.mts.usersservice.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Boolean nightTheme;
    private String language;
    private Boolean telVisible;
    private Boolean whatsappVisible;
    @OneToOne(mappedBy = "preference")
    private Client client;

}
