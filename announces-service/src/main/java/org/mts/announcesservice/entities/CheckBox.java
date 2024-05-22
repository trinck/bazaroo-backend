package org.mts.announcesservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.mts.announcesservice.enums.FieldType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CheckBox extends Check{

    @OneToMany(orphanRemoval = true, cascade = CascadeType.PERSIST)
    private Set<CheckUnit> checked = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private final FieldType type = FieldType.CHECKBOX;

    public boolean addChecked(CheckUnit checkUnit){
       if(this.checkUnits.contains(checkUnit)) return  this.checked.add(checkUnit);
       return false;
    }


    /**
     * @return
     */
    @Override
    public FieldType getType() {
        return this.type;
    }
}
