package org.mts.imagesservice.dtos;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageContentOutputDTO extends MediaOutputDTO {


    private String urlHigh;
    private String dimensions;
}
