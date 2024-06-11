package org.mts.announcesservice.remote_entities;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Media {

    private Long id;

    // Mime type of the media (e.g. "image/png", "image/jpeg", "video/mp4")
    private String type;

    // Name of the media (e.g. "my-image.png")
    private String name;

    private String url;

    private String path;

    private Long size;
}
