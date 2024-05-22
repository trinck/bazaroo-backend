package org.mts.announcesservice.web;


import jakarta.validation.constraints.NotEmpty;
import org.modelmapper.ModelMapper;
import org.mts.announcesservice.dtos.CategoryInputDTO;
import org.mts.announcesservice.dtos.CategoryOutputDTO;
import org.mts.announcesservice.entities.Category;
import org.mts.announcesservice.service.ICategoryService;
import org.mts.announcesservice.utilities.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("categories")
public class CategoryController {


    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;


    @PostMapping
    public CategoryOutputDTO create(@RequestBody CategoryInputDTO inputDTO){

        Category category = this.modelMapper.map(inputDTO, Category.class);
        if(inputDTO.getParentId()!=null && !inputDTO.getParentId().isEmpty()){

            Category parent = this.categoryService.getByID(inputDTO.getParentId());
            category.setParentCategory(parent);
        }
        return this.modelMapper.map(this.categoryService.create(category),CategoryOutputDTO.class);
    }


    @PutMapping("/{id}")
    public  CategoryOutputDTO update(@PathVariable @NotEmpty String id, @RequestBody CategoryInputDTO dto){
        Category category = this.modelMapper.map(dto, Category.class);
        category.setId(id);
        return this.modelMapper.map(this.categoryService.update(category),CategoryOutputDTO.class);
    }


    @GetMapping("/{id}")
    public CategoryOutputDTO getById(@PathVariable String id){
        return this.modelMapper.map(this.categoryService.getByID(id),CategoryOutputDTO.class);
    }

    @DeleteMapping("/{id}")
    public CategoryOutputDTO delete(@PathVariable String id){
        return this.modelMapper.map(this.categoryService.deleteById(id),CategoryOutputDTO.class);
    }

    @GetMapping
    public Map<String, Object> getAll(@RequestParam(defaultValue = "5") int size,@RequestParam(defaultValue = "0") int page){

        Page<Category> categories = this.categoryService.getCategories(PageRequest.of(page, size));
        Map<String, Object> map = WebUtils.pageToMap(categories);
        map.put("content", categories.getContent().stream().map(c->this.modelMapper.map(c, CategoryOutputDTO.class)).toList());
        return map;
    }

}
