package org.mts.imagesservice.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.mts.imagesservice.entities.IconContent;
import org.mts.imagesservice.entities.ImageContent;
import org.mts.imagesservice.entities.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class ServiceStorageCloud implements IServiceStorage{
    /**
     *
     */

    @Autowired
    Cloudinary cloudinary;

    @Override
    public void initRootsStorage() {

    }

    /**
     * @param filename
     * @param source
     * @return
     */
    @Override
    public Path load(String filename, Path source) {
        return null;
    }

    /**
     * @param filename
     * @param source
     * @return
     */
    @Override
    public Resource loadAsResource(String filename, Path source) {
        return null;
    }

    /**
     * @param source
     */
    @Override
    public void deleteAll(String source) {

    }

    /**
     * @param files
     * @param source
     * @param id
     * @param mediaType
     * @return
     */
    @Override
    public List<Media> storeAll(List<MultipartFile> files, String source, String id, Class<? extends Media> mediaType) {
        List<Media> medias = new ArrayList<>();
        for (MultipartFile file : files) {

           // String fileName = file.getOriginalFilename();
            Map params = ObjectUtils.asMap("folder","announces/"+id);
           try {
               Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
               Media media = mediaType.getName().equals(ImageContent.class.getName()) ? new ImageContent() : new IconContent();
               media.setSize(file.getSize());
               media.setType(file.getContentType());
               media.setName(file.getName());
               media.setUrl(uploadResult.get("url").toString());
               media.setPath(uploadResult.get("url").toString());
               medias.add(media);
           }catch (IOException e){
               System.out.println(e.getMessage());
           }


        }

        return medias;
    }

    /**
     * @param source
     * @param dept
     * @return
     */
    @Override
    public Stream<Path> loadAll(String source, int dept) {
        return Stream.empty();
    }

    /**
     * @param file
     * @param filename
     * @param id
     * @param root
     * @param mediaClass
     * @return
     */
    @Override
    public Media store(MultipartFile file, String filename, String id, Path root, Class<? extends Media> mediaClass) {
        return null;
    }
}
