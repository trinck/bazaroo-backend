package org.mts.imagesservice.entities;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DiscriminatorValue(value = "image")
public class ImageContent extends Media{

    private String urlHigh;
    private String dimensions;

}
