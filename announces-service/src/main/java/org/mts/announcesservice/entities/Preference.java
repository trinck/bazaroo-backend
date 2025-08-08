package org.mts.announcesservice.entities;

import jakarta.persistence.*;
import jakarta.ws.rs.DefaultValue;
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
    private Boolean beNotified = true;
    private Boolean alertStatus = true;
    private Boolean alertSearch = true;
    private Boolean active = true;
    private Boolean mail = true;
    @Column(nullable = false)
    private String userId;
    private String roles;
    private Boolean enabled = true;
}
