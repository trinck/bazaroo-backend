package org.mts.announcesservice.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.announcesservice.enums.FieldType;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String fieldName;
    @Enumerated(EnumType.STRING)
    private FieldType type;

    @ManyToOne
    private AnnounceType announceType;
}
