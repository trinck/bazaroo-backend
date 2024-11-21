package org.mts.announcesservice.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;
import org.mts.announcesservice.enums.FieldType;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "booleans")
public class Bool extends Field{

    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Boolean)
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
