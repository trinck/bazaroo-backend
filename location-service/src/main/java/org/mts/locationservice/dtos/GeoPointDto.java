package org.mts.locationservice.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link org.mts.locationservice.entities.GeoPoint}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GeoPointDto implements Serializable {
    private  Long id;
    private  Double lat;
    private  Double lon;
}