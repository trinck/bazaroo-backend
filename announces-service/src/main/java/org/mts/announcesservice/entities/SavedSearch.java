package org.mts.announcesservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.TenantId;
import org.mts.announcesservice.enums.SavedSearchScheduleType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SavedSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @TenantId
    @Column(name = "tenant_id", nullable = false)
    private String tenantId;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SavedSearchScheduleType type;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime time;

    @Column(nullable = false)
    private String userId;
    private String searchName; // Un nom personnalisé pour la recherche

    @Column(nullable = false, length = 1000)
    private String criteria;  // JSON contenant les filtres (ex : {"category": "smartphone", "maxPrice": 500})

    @Column(nullable = false)
    private Boolean active; // Si la recherche est active ou non

    private Date createdAt; // Date de création
    private Date beginDate;

}
