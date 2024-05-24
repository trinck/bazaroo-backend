package org.mts.announcesservice.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.announcesservice.enums.FieldType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String id;
    protected String name;
    @ManyToOne
    protected Announce announce;

    public abstract FieldType getType();

}
