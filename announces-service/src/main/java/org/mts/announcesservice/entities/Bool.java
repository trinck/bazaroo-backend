package org.mts.announcesservice.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;
import org.mts.announcesservice.enums.FieldType;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "BBoolean")
public class Bool extends Field{

    private Boolean dataValue = true;
    @Enumerated(EnumType.STRING)
    private final FieldType type = FieldType.BOOLEAN;

    /**
     * @return
     */
    @Override
    public FieldType getType() {
        return this.type;
    }
}
