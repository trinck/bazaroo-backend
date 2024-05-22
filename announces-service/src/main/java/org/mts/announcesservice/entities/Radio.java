package org.mts.announcesservice.entities;


import jakarta.persistence.*;
import lombok.*;
import org.mts.announcesservice.enums.FieldType;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Radio extends Check{

    @OneToOne
    private CheckUnit checked;
    @Enumerated(EnumType.STRING)
    private final FieldType type = FieldType.RADIO;

    public boolean setChecked(CheckUnit checkUnit){
        if(this.checkUnits.contains(checkUnit)){
            this.checked = checkUnit;
            return  true;
        }
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
