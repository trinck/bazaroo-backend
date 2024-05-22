package org.mts.announcesservice.dtos;



import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnounceTypeInputDTO {
    private String id;
    @NotEmpty(message = "Name type must be initialized")
    private String name;

}
