package org.mts.announcesservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.mts.announcesservice.enums.FieldType;



@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "checkbox")

public class CheckBox extends Check{

    @Enumerated(EnumType.STRING)
    private final FieldType type = FieldType.CHECKBOX;


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
       this.checkUnits.stream().filter(c->c.getId().equals(id)).findFirst().ifPresent(unit -> unit.setChecked(!unit.getChecked()));
    }
}
