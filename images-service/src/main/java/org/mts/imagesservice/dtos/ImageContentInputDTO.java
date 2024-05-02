package org.mts.imagesservice.dtos;



import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ImageContentInputDTO extends MediaInputDTO{

    private String urlHigh;
    private String dimensions;
}
