package org.mts.announcesservice.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "categories")
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String title;
    private String iconUrl;
    private String description;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.PERSIST)
    private List<Category> subCategories = new ArrayList<>();

    @ManyToOne
    private Category parentCategory;

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<AnnounceType> types = new ArrayList<>();

}
