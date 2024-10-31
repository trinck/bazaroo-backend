package org.mts.announcesservice.entities;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.announcesservice.enums.AnnounceStatus;

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
    private String streetId;
    @OneToOne(mappedBy = "announce")
    private GeoZone location;
    private String title;
    private String tel;
    private Date postedAt;
    private String address;
    private String description;
    private String userId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AnnounceStatus status = AnnounceStatus.ACTIVE;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;
    @OneToMany(mappedBy = "announce", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Field> fields = new ArrayList<>();


    public void setType(AnnounceType type) {
        if (this.category != null) {

            List<Category> categories = new ArrayList<>();
            categories.add(this.category);
            categories.addAll(this.getParents(this.category));
            List<AnnounceType> types = categories.stream().map(Category::getTypes).filter(Objects::nonNull).reduce((t, t2) -> {
                t.addAll(t2);
                return t;
            }).orElse(new ArrayList<>());
            if (types.contains(type)) {
                this.type = type;
            } else {
                throw new NoSuchElementException("Type with name: " + type.getName() + " isn't present in hierarchic category");
            }

        } else {
            throw new IllegalArgumentException("Category unspecified");
        }
    }


    public void addField(Field field) {

        if (this.type == null) throw new NullPointerException("Announce type is null");

        List<CategoryField> list = this.type.getFields()
                .stream().filter(f -> {
                    return f.getFieldName().equals(field.getName()) && f.getType() == field.getType();
                }).toList();

        if (list.size() != 1)
            throw new UnsupportedOperationException("No field with name " + field.getName() + " in category type");
        if (fields.stream().anyMatch(f -> field.getName().equals(f.getName())))
            throw new IllegalArgumentException("Element already present");
        field.setAnnounce(this);

        if (field instanceof Check) {
            CategoryFieldCheck ch = (CategoryFieldCheck) list.stream().findFirst().orElseThrow();
            if (((Check) field).getCheckUnits().size() != ch.getFieldCheckUnits().size())
                throw new IllegalArgumentException("more or less required elements ");
            testCheckUnitMatch((Check) field, ch);
        }
        this.fields.add(field);

    }


    /**
     * Retrieve all parent categories of the given category.
     *
     * @param category the category to find parents for
     * @return a list of parent categories
     */
    private List<Category> getParents(Category category) {
        List<Category> parents = new ArrayList<>();
        Category current = category.getParentCategory();

        while (current != null) {
            parents.add(current);
            current = current.getParentCategory();
        }
        return parents;
    }


    /**
     * Verify if all check units match with all category check units
     *
     * @param check the field check to add with his check units
     * @param ch    the category field check with required category field check units to match
     */
    private void testCheckUnitMatch(Check check, CategoryFieldCheck ch) {

        for (CheckUnit unit : check.getCheckUnits()) {
            boolean isCheckUnitPresent = ch.getFieldCheckUnits().stream().anyMatch(u -> {
                return unit.getName().equals(u.getName()) && unit.getDataValue().equals(u.getDataValue());
            });
            if (!isCheckUnitPresent)
                throw new IllegalArgumentException("Check unit " + unit.getName() + " is not necessary");
        }

    }


}
