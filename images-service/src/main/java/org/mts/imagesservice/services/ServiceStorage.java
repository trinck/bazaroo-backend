package org.mts.imagesservice.services;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.imagesservice.configs.StorageMediasSources;
import org.mts.imagesservice.entities.IconContent;
import org.mts.imagesservice.entities.ImageContent;
import org.mts.imagesservice.entities.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;


@Data
@NoArgsConstructor
public class ServiceStorage implements IServiceStorage {


   // @Autowired
    private StorageMediasSources mediasSources;


    @Override
    public void initRootsStorage() {

        Path adverts = Paths.get(this.mediasSources.getAdverts());
        Path profiles = Paths.get(this.mediasSources.getProfiles());
        Path icons = Paths.get(this.mediasSources.getIcons());
        creatDirectory(adverts);
        creatDirectory(profiles);
        creatDirectory(icons);

    }


    private Path creatDirectory(Path path) {

        try {
            return Files.createDirectories(path);

        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for medias folder!");
        }

    }


    @Override
    public Path load(String filename, Path source) {

        try {
            Path file = source.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return (Path) resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    @Override
    public Resource loadAsResource(String filename, Path source) {

        try {
            Path file = source.resolve(filename);
            Resource resource = new UrlResource(source.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    @Override
    public void deleteAll(String source) {

        Path rootMedia = Paths.get(source);
        FileSystemUtils.deleteRecursively(rootMedia.toFile());
    }

    /**
     * @param path

     */
    @Override
    public void deleteAllMedias(String path) {

    }


    @Override
    public List<Media> storeAll(List<MultipartFile> files, String source, String id, Class<? extends Media> mediaType) {

        List<Media> medias = new ArrayList<>();

        Path path = Paths.get(source);
        creatDirectory(path);

        for (MultipartFile file : files) {

            String fileName = file.getOriginalFilename();
            medias.add(store(file, fileName, id, path, mediaType));

        }

        return medias;
    }

    /**
     * @param file
     * @param source
     * @param id
     * @param mediaType
     * @return
     */
    @Override
    public Media store(MultipartFile file, String source, String id, Class<? extends Media> mediaType) {
        return null;
    }


    @Override
    public Stream<Path> loadAll(String source, int dept) {

        Path pathMedia = Paths.get(source);
        try {
            return Files.walk(pathMedia, dept).filter(path -> !path.equals(pathMedia)).map(pathMedia::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }

    }


    @Override
    public Media store(MultipartFile file, String filename, String id, Path root, Class<? extends Media> mediaClass) {
        String mediaType = mediaClass.getTypeName();
        Media media = mediaType.equals(ImageContent.class.getName()) ? new ImageContent() : new IconContent();
        media.setSize(file.getSize());
        media.setType(file.getContentType());

        if (id != null && !id.isEmpty() && !root.resolve(id).toFile().exists()) {
            root = creatDirectory(root.resolve(id));

        } else if (id != null && !id.isEmpty()) {
            root = root.resolve(id);
        }


        try {
            Path resolvedPath = root.resolve(filename);
            Files.copy(file.getInputStream(), resolvedPath);
            media.setName(filename);
            root = resolvedPath;
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {

                try {

                    Date date = new Date();
                    String compositeName = date.getTime() + filename;
                    Path resolvedPath = root.resolve(compositeName);
                    Files.copy(file.getInputStream(), resolvedPath);
                    media.setName(compositeName);
                    root = resolvedPath;

                } catch (IOException eio) {
                    // TODO Auto-generated catch block
                    eio.printStackTrace();
                }

            }

            e.printStackTrace();
        }


        String[] partPath = root.toString().split("\\\\");
        media.setPath(String.join("/", partPath));
        return media;
    }

}
