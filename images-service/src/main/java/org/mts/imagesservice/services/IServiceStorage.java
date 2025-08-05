package org.mts.imagesservice.services;

import org.mts.imagesservice.entities.Media;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface IServiceStorage {

    public void initRootsStorage() ;

    public Path load(String filename, Path source) ;
    public Resource loadAsResource(String filename, Path source);

    public void deleteAll(String source) ;

    public List<Media> storeAll(List<MultipartFile> files, String source,String id, Class<? extends Media> mediaType);
    public Media store(MultipartFile file, String source,String id, Class<? extends Media> mediaType);

    public Stream<Path> loadAll(String source, int dept) ;

    public Media store(MultipartFile file, String filename,String id, Path root,Class<? extends Media> mediaClass);

}
