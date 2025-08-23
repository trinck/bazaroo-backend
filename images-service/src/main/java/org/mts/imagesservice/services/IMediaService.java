package org.mts.imagesservice.services;

import org.mts.imagesservice.entities.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMediaService {


    public Media getMediaById(Long id);
    public Page<Media> getMediasByNameContains(String name, Pageable pageable);
    public Page<Media> getMediasByTypeContains(String type, Pageable pageable);
    public List<Media> getAllMedias();
    public Media save(Media media);
    public Media update(Media media);
    public List<Media> addAll(List<Media> medias);
    public Media deleteMediaById(Long id);
    public void deleteAllByAdId(String id);
    public List<Media> getMediasByPathContains(String path);

}
