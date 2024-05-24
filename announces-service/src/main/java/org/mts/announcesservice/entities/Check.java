package org.mts.announcesservice.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public abstract class Check extends Field {

    @OneToMany(mappedBy = "check", orphanRemoval = true, cascade = CascadeType.PERSIST)
    protected List<CheckUnit> checkUnits = new ArrayList<>();


    protected void addCheckUnit(CheckUnit unit){
        this.checkUnits.add(unit);
    }

    protected boolean removeCheckUnit(CheckUnit unit){
       return this.checkUnits.remove(unit);
    }

    public abstract void check(Long id);
}
