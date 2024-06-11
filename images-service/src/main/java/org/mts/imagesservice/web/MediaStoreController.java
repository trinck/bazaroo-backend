package org.mts.imagesservice.web;


import org.modelmapper.ModelMapper;
import org.mts.imagesservice.configs.StorageMediasSources;
import org.mts.imagesservice.dtos.ImageContentOutputDTO;
import org.mts.imagesservice.dtos.MediaOutputDTO;
import org.mts.imagesservice.entities.ImageContent;
import org.mts.imagesservice.entities.Media;
import org.mts.imagesservice.services.IMediaService;
import org.mts.imagesservice.services.IServiceStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("mediaStore")
public class MediaStoreController {

    @Autowired
    private IMediaService mediaService;
    @Autowired
    private IServiceStorage serviceStorage;
    @Autowired
    private StorageMediasSources mediasSources;
    @Autowired
    private ModelMapper modelMapper;


    @PostMapping(value = "/profiles/{id}/save")
    public ImageContentOutputDTO addProfile(@RequestParam MultipartFile file, @PathVariable String id){

        String contentType = file.getContentType();
        if (!("image/png".equals(contentType) || "image/jpeg".equals(contentType))) {
            throw new IllegalArgumentException( "Only PNG and JPEG images are allowed");
        }
        ImageContent media = (ImageContent) this.serviceStorage.store(file, file.getOriginalFilename(),id, Paths.get(mediasSources.getProfiles()),ImageContent.class);
        media.setUrl("mediaStore/profiles/"+id+"/load/");
        return modelMapper.map(this.mediaService.save(media), ImageContentOutputDTO.class);
    }

    @GetMapping("/adverts/{announceId}")
    public List<MediaOutputDTO> getAdvertMedias(@PathVariable String announceId){
        return this.mediaService.getMediasByPathContains(this.mediasSources.getAdverts()+"/"+announceId+"/").stream().map(m->this.modelMapper.map(m, MediaOutputDTO.class)).toList();
    }


    @PostMapping("/adverts/{id}/save-all")
    public List<MediaOutputDTO> addAdverts(@RequestParam List<MultipartFile> files, @PathVariable String id){

        List<Media> mediaList = serviceStorage.storeAll(files, mediasSources.getAdverts(), id,ImageContent.class);
        mediaList.forEach(media ->media.setUrl("mediaStore/adverts/"+id+"/load/"));
        return this.mediaService.addAll(mediaList).stream().map(media -> modelMapper.map(media, MediaOutputDTO.class)).toList();
    }


    @PostMapping("/adverts/{id}/save")
    public MediaOutputDTO addAdvert(@RequestParam MultipartFile file, @PathVariable String id){

        Media media =  this.serviceStorage.store(file, file.getOriginalFilename(),id, Paths.get(mediasSources.getAdverts()),ImageContent.class);
        media.setUrl("mediaStore/adverts/"+id+"/load/");
        return modelMapper.map(this.mediaService.save(media), MediaOutputDTO.class);
    }



    @GetMapping("/adverts/{advertId}/load/{mediaId}")
    public ResponseEntity<byte[]> loadAdvert(@PathVariable String advertId, @PathVariable Long mediaId){

        try {
            Media media = this.mediaService.getMediaById(mediaId);
            Resource videoResource = this.serviceStorage.loadAsResource(media.getName(), Paths.get(media.getPath()));

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(media.getType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + media.getName() + "\"")
                    .body(videoResource.getContentAsByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (NoSuchElementException exc){
            throw new NoSuchElementException(exc);
        }

    }



    @GetMapping("/profiles/{profileId}/load/{mediaId}")
    public ResponseEntity<byte[]> loadProfile(@PathVariable String profileId, @PathVariable Long mediaId){

           try {
               Media media = this.mediaService.getMediaById(mediaId);
                 Resource videoResource = this.serviceStorage.loadAsResource(media.getName(), Paths.get(media.getPath()));

               return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(media.getType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + media.getName() + "\"")
                    .body(videoResource.getContentAsByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchElementException exc){
            throw new NoSuchElementException(exc);
           }

    }




}
