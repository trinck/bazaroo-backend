package org.mts.imagesservice.dtos;

import lombok.*;
import org.mts.imagesservice.entities.GenericsFieldsEntity;


@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaOutputDTO extends GenericsFieldsEntity {

    private Long id;

    // Mime type of the media (e.g. "image/png", "image/jpeg", "video/mp4")
    private String type;

    // Name of the media (e.g. "my-image.png")
    private String name;

    private String url;

    private Long size;
}
