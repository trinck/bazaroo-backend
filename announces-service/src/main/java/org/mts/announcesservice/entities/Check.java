package org.mts.announcesservice.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public abstract class Check extends Field {

    @OneToMany(mappedBy = "check", orphanRemoval = true, cascade = CascadeType.PERSIST)
    @ToString.Exclude
    protected List<CheckUnit> checkUnits = new ArrayList<>();


    protected void addCheckUnit(CheckUnit unit){
        this.checkUnits.add(unit);
    }

    protected boolean removeCheckUnit(CheckUnit unit){
       return this.checkUnits.remove(unit);
    }

    public abstract void check(Long id);

}
