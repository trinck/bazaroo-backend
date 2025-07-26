package org.mts.notificationservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.mts.notificationservice.enums.PreferredCanal;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public  class Preference implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private boolean newUser;
    private boolean newAd;
    private boolean updatedUser;
    private boolean suggestedAd;
    @Enumerated(EnumType.STRING)
    private PreferredCanal preferredCanal;
}
