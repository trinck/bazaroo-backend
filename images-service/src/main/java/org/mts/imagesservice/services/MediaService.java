package org.mts.imagesservice.services;

import jakarta.transaction.Transactional;
import org.mts.imagesservice.configs.StorageMediasSources;
import org.mts.imagesservice.entities.Media;
import org.mts.imagesservice.repositories.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaService implements IMediaService{


   private final MediaRepository mediaRepository;
   private final IServiceStorage serviceStorage;
   private final StorageMediasSources mediasSources;

    public MediaService(MediaRepository mediaRepository, IServiceStorage serviceStorage, StorageMediasSources mediasSources) {
        this.mediaRepository = mediaRepository;
        this.serviceStorage = serviceStorage;
        this.mediasSources = mediasSources;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Media getMediaById(Long id) {
        return this.mediaRepository.findById(id).orElseThrow();
    }

    /**
     * @param name
     * @param pageable
     * @return
     */
    @Override
    public Page<Media> getMediasByNameContains(String name, Pageable pageable) {
        return this.mediaRepository.findByNameContains(name,pageable);
    }

    /**
     * @param type
     * @param pageable
     * @return
     */
    @Override
    public Page<Media> getMediasByTypeContains(String type, Pageable pageable) {
        return this.mediaRepository.findByTypeContains(type,pageable);
    }

    /**
     * @return
     */
    @Override
    public List<Media> getAllMedias() {
        return this.mediaRepository.findAll();
    }

    /**
     * @param media
     * @return
     */
    @Override
    @Transactional
    public Media save(Media media) {
        return this.mediaRepository.save(media);
    }

    /**
     * @param media
     * @return
     */
    @Override
    public Media update(Media media) {
        return this.mediaRepository.save(media);
    }

    /**
     * @param medias
     * @return
     */
    @Override
    @Transactional
    public List<Media> addAll(List<Media> medias) {
        return this.mediaRepository.saveAll(medias);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Media deleteMediaById(Long id) {

        Media media = this.mediaRepository.findById(id).orElseThrow();
        this.mediaRepository.deleteById(id);

        return media;
    }

    /**
     * @param id
     */
    @Override
    public void deleteAllByAdId(String id) {
        try {
            List<Media> media = this.getMediasByPathContains(this.mediasSources.getAdverts()+"/"+id+"/");

            if (!media.isEmpty()) {
                this.mediaRepository.deleteAll(media);
                this.serviceStorage.deleteAllMedias(this.mediasSources.getAdverts()+"/"+id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param path
     * @return
     */
    @Override
    public List<Media> getMediasByPathContains(String path) {
        return this.mediaRepository.findAllByPathContains(path);
    }


}
