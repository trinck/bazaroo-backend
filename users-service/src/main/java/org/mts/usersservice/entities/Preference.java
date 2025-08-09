package org.mts.usersservice.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean nightTheme = false;
    private Boolean alertAccount = true;
    private String language;
    private Boolean telVisible = true;
    private Boolean whatsappVisible = true;
    private String userId;
    private String roles;
    private Boolean enabled = true;
    @ElementCollection
    private List<String> favoriteAds = new ArrayList<>();

}
