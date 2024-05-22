package org.mts.announcesservice.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public abstract class Check extends Field {

    @OneToMany(mappedBy = "check", orphanRemoval = true, cascade = CascadeType.PERSIST)
    protected Set<CheckUnit> checkUnits = new HashSet<>();


    protected void addCheckUnit(CheckUnit unit){
        this.checkUnits.add(unit);
    }

    protected boolean removeCheckUnit(CheckUnit unit){
       return this.checkUnits.remove(unit);
    }
}
