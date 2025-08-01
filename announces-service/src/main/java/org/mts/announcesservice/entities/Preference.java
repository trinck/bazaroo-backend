package org.mts.announcesservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean beNotified;
    private boolean expired;
    private boolean active;
    private boolean mail;
    @Column(nullable = false)
    private String userId;
    private String roles;
    private boolean enabled;
}
