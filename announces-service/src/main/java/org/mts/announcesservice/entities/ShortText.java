package org.mts.announcesservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.mts.announcesservice.enums.FieldType;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortText extends Field{

    @Size(max = 255, message = "Short text size must be smaller than 255")
    @Column(name = "shortTextValue")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
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
