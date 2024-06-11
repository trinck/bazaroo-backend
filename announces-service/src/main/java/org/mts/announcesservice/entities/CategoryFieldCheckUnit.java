package org.mts.announcesservice.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class CategoryFieldCheckUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String name;
    private String dataValue = name;
    @ManyToOne
    @JsonIgnore
    private CategoryFieldCheck categoryField;
}
