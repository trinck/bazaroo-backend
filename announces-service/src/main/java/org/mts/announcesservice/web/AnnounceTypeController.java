package org.mts.announcesservice.web;


import jakarta.validation.constraints.NotEmpty;
import org.modelmapper.ModelMapper;
import org.mts.announcesservice.dtos.AnnounceTypeInputDTO;
import org.mts.announcesservice.dtos.AnnounceTypeOutputDTO;
import org.mts.announcesservice.dtos.CategoryOutputDTO;
import org.mts.announcesservice.entities.AnnounceType;
import org.mts.announcesservice.entities.Category;
import org.mts.announcesservice.service.IAnnounceTypeService;
import org.mts.announcesservice.service.ICategoryService;
import org.mts.announcesservice.utilities.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("announceTypes")
public class AnnounceTypeController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IAnnounceTypeService announceTypeService;
    @Autowired
    private ICategoryService categoryService;


    @PostMapping("/{id}")
    public AnnounceTypeOutputDTO create(@PathVariable String id, @RequestBody AnnounceTypeInputDTO dto){
        AnnounceType announceType = this.modelMapper.map(dto, AnnounceType.class);
        Category category = this.categoryService.getByID(id);
        List<AnnounceType> exists =  category.getTypes().stream().filter(t->t.getName().equals(announceType.getName())).toList();

       if(!exists.isEmpty()){throw new IllegalArgumentException("Type with name "+announceType.getName()+" already exists");}

        announceType.setCategory(category);
        return  this.modelMapper.map(this.announceTypeService.create(announceType), AnnounceTypeOutputDTO.class);
    }

    @GetMapping("/{id}")
    public AnnounceTypeOutputDTO getById(@PathVariable String id){
        return this.modelMapper.map(this.announceTypeService.getByID(id), AnnounceTypeOutputDTO.class);
    }


    @PutMapping("/{id}")
    public AnnounceTypeOutputDTO update(@PathVariable @NotEmpty String id, @RequestBody AnnounceTypeInputDTO dto){
        AnnounceType  type = this.modelMapper.map(dto, AnnounceType.class);
        type.setId(id);
        return this.modelMapper.map(this.announceTypeService.update(type), AnnounceTypeOutputDTO.class);
    }


    @DeleteMapping("/{id}")
    public  AnnounceTypeOutputDTO delete(@PathVariable String id){
        return this.modelMapper.map(this.announceTypeService.deleteById(id), AnnounceTypeOutputDTO.class);
    }

    @GetMapping
    public Map<String, Object> getAll(@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") int page){

        Page<AnnounceType> types = this.announceTypeService.getAnnounceTypes(PageRequest.of(page, size));
        Map<String, Object> map = WebUtils.pageToMap(types);
        map.put("content", types.getContent().stream().map(c->this.modelMapper.map(c, AnnounceTypeOutputDTO.class)).toList());
        return map;
    }
}
