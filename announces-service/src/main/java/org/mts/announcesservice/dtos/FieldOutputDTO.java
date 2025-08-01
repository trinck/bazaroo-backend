package org.mts.announcesservice.dtos;


import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.announcesservice.entities.CategoryFieldCheckUnit;
import org.mts.announcesservice.enums.FieldType;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldOutputDTO {
    @Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Keyword)
    protected String id;
    @NotEmpty
    @Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    protected String name;
    @Transient
    protected Object dataValue;
    @Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    protected String dataValueString;
    @Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Double)
    protected Double dataValueDouble;
    @Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Boolean)
    protected Boolean dataValueBoolean;
    @Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private  FieldType type;
    private List<CheckUnitOutputDTO> fieldCheckUnits;


    // Méthode pour préparer l'entité avant l'indexation
    public void prepareForIndexing() {
        if (this.dataValue instanceof String) {
            this.dataValueString = (String) this.dataValue;
            this.dataValueDouble = null; // Assurez-vous que l'autre champ est vide
            this.dataValueBoolean = null;
        } else if (this.dataValue instanceof Double) {
            this.dataValueDouble = (Double) this.dataValue;
            this.dataValueString = null; // Assurez-vous que l'autre champ est vide
            this.dataValueBoolean = null;
        } else if (this.dataValue instanceof Boolean) {
            this.dataValueBoolean = (Boolean) this.dataValue;
            this.dataValueString = null;
            this.dataValueDouble = null;
        } else {
            // Cas où monChamp est null ou d'un type inattendu
            this.dataValueString = null;
            this.dataValueDouble = null;
            this.dataValueBoolean = null;
        }
    }

}
