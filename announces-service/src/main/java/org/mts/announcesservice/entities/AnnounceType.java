package org.mts.announcesservice.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnounceType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    private String id;
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @OneToMany(mappedBy = "announceType", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CategoryField> fields = new ArrayList<>();


}
