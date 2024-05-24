package org.mts.announcesservice.entities;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Announce {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(unique = false, name = "type_id")
    private AnnounceType type;
    private Double price;
    private String cityId;
    private Long locationId;
    private String title;
    private Date postedAt;
    private String address;
    private String description;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;
    @OneToMany(mappedBy = "announce", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Field> fields = new ArrayList<>();



    public void setType(AnnounceType type){
        if(this.category!= null){

            List<Category> categories = new ArrayList<>();
            categories.add(this.category);
            categories.addAll(this.getParents(this.category));
            List<AnnounceType> types = categories.stream().map(Category::getTypes).filter(Objects::nonNull).reduce((t, t2) -> {t.addAll(t2); return t;}).orElse(new ArrayList<>());
            if(types.contains(type)){
                this.type = type;
            }else {throw  new NoSuchElementException("Type with name: "+type.getName()+" isn't present in hierarchic category");}

        }else {
            throw new IllegalArgumentException("Category unspecified");
        }
    }



    public void addField(Field field){

        if(this.type!= null){
            List<CategoryField> list = this.type.getFields().stream().filter(f -> {return f.getFieldName().equals(field.getName()) && f.getType() == field.getType();}).toList();
            if(list.size() == 1 && !fields.contains(field)){
               field.setAnnounce(this);
                this.fields.add(field);
            }
        }else throw new NullPointerException("Announce type is null");
    }





    /**
     * Retrieve all parent categories of the given category.
     *
     * @param category the category to find parents for
     * @return a list of parent categories
     */
    private List<Category> getParents(Category category){
        List<Category> parents = new ArrayList<>();
        Category current = category.getParentCategory();

        while (current != null) {
            parents.add(current);
            current = current.getParentCategory();
        }
        return parents;
    }



}
