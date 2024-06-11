package org.mts.imagesservice.services;

import jakarta.transaction.Transactional;
import org.mts.imagesservice.entities.Media;
import org.mts.imagesservice.repositories.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaService implements IMediaService{

   @Autowired
   private MediaRepository mediaRepository;

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

        Media mediaSaved = this.mediaRepository.save(media);
        media.setUrl(mediaSaved.getUrl()+mediaSaved.getId());
        return mediaSaved;
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
        List<Media> mediasList = this.mediaRepository.saveAll(medias);
        for (Media m: mediasList){
            m.setUrl(m.getUrl()+m.getId());
        }
        return mediasList;
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
     * @param path
     * @return
     */
    @Override
    public List<Media> getMediasByPathContains(String path) {
        return this.mediaRepository.findAllByPathContains(path);
    }


}
