package org.mts.announcesservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Announce {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    private AnnounceType type;
    private Double price;
    private String CityId;
    private Long locationId;
    private String title;
    private Date postedAt;
    private String address;
    private String description;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "announce", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<Field> fields = new HashSet<>();



    public void setType(AnnounceType type){
        if(this.category!= null && this.category.getTypes().contains(type)){
            this.type = type;
        }else {
            throw new IllegalArgumentException("Incorrect type for this announce or category unspecified");
        }
    }

    public void setField(Field field){
        if(this.type!= null){
            List<CategoryField> list = this.type.getFields().stream().filter(f -> {return f.getFieldName().equals(field.getName()) && f.getType() == field.getType();}).toList();
            if(list.size() == 1 && !fields.contains(field)){
               field.setAnnounce(this);
                this.fields.add(field);
            }
        }
    }

    public void setFields(Set<Field> fields) {
        for(Field field:fields){
            this.setField(field);
        }
    }
}
