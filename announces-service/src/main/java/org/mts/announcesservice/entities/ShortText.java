package org.mts.announcesservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.mts.announcesservice.enums.FieldType;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortText extends Field{

    @Size(max = 255, message = "Short text size must be smaller than 255")
    private String dataValue = "";

    @Enumerated(EnumType.STRING)
    private final FieldType type = FieldType.SHORT_TEXT;

    /**
     * @return
     */
    @Override
    public FieldType getType() {
        return this.type;
    }
}
