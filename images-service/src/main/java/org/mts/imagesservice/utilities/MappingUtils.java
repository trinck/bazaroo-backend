package org.mts.imagesservice.utilities;


import org.modelmapper.ModelMapper;
import org.mts.imagesservice.entities.Media;
import org.springframework.stereotype.Service;


@Service
public class MappingUtils {

    private final  ModelMapper mapper;

    public MappingUtils(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public  <T,E> T map(E enter, Class<T> target){
         return  mapper.map(enter,target);
    }

}
