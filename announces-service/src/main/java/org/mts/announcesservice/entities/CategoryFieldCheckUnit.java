package org.mts.announcesservice.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryFieldCheckUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String name;
    private String dataValue = name;
    private Boolean checked;
    @ManyToOne
    @JsonBackReference
    private CategoryFieldCheck categoryField;
}
