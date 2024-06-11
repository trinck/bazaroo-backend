package org.mts.announcesservice.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("CategoryFieldCheck")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryFieldCheck extends CategoryField{
    @OneToMany(mappedBy = "categoryField", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CategoryFieldCheckUnit> fieldCheckUnits = new ArrayList<>();
}
