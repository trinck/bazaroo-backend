package org.mts.announcesservice.entities;


import jakarta.persistence.*;
import lombok.*;
import org.mts.announcesservice.enums.FieldType;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Builder
public class Radio extends Check{

    @Enumerated(EnumType.STRING)
    private final FieldType type = FieldType.RADIO;


    /**
     * @return
     */
    @Override
    public FieldType getType() {
        return this.type;
    }

    /**
     * @param id
     */
    @Override
    public void check(Long id) {
        this.checkUnits.stream().filter(u-> u.getChecked().equals(true)).forEach(u->u.setChecked(false));
        this.checkUnits.stream().filter(u->u.getId().equals(id)).findFirst().ifPresent(u->u.setChecked(!u.getChecked()));
    }
}
