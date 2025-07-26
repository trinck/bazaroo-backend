package org.mts.announcesservice.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.mts.announcesservice.enums.FieldType;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "classe")
@DiscriminatorValue("CategoryField")
@ToString
public class CategoryField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @NotEmpty
    protected String fieldName;
    @Enumerated(EnumType.STRING)
    protected FieldType type;
    @ManyToOne
    private AnnounceType announceType;
}
