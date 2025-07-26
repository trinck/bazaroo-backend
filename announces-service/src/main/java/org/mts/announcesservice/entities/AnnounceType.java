package org.mts.announcesservice.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnounceType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    private String id;

    @Column(nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Text)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @OneToMany(mappedBy = "announceType", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CategoryField> fields = new ArrayList<>();


    public void addCategoryField(CategoryField field){
        if(fields.stream().anyMatch(f->f.getFieldName().equals(field.getFieldName())))throw new IllegalArgumentException("Field with name "+field.getFieldName()+" already exists");;
        field.setAnnounceType(this);
        if(field instanceof CategoryFieldCheck){
            ((CategoryFieldCheck) field).getFieldCheckUnits().forEach(u->u.setCategoryField((CategoryFieldCheck)field));
        }
        this.fields.add(field);
    }
}
