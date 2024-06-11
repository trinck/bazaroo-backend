package org.mts.announcesservice.entities;


import jakarta.persistence.*;
import lombok.*;
import org.mts.announcesservice.enums.FieldType;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "texts")
public class Text extends Field{
    @Column(length = 500, name = "textValue")
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
