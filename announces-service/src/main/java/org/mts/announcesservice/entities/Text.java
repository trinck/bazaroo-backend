package org.mts.announcesservice.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.mts.announcesservice.enums.FieldType;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Text extends Field{
    @Column(length = 500)
    private String dataValue = "";

    @Enumerated(EnumType.STRING)
    private final FieldType type = FieldType.TEXT;

    /**
     * @return
     */
    @Override
    public FieldType getType() {
        return this.type;
    }
}
