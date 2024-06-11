package org.mts.announcesservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.announcesservice.remote_entities.Media;

import java.util.List;


@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class AnnounceWithMedias {

        private AnnounceOutputDTO announce;
        private List<Media> medias;

}
