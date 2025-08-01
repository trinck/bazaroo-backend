package org.mts.announcesservice.web;


import jakarta.validation.constraints.NotEmpty;
import org.modelmapper.ModelMapper;
import org.mts.announcesservice.dtos.CategoryFieldObjectInputDTO;
import org.mts.announcesservice.dtos.CategoryFieldInputDTO;
import org.mts.announcesservice.dtos.CategoryFieldOutputDTO;
import org.mts.announcesservice.entities.CategoryField;
import org.mts.announcesservice.entities.AnnounceType;
import org.mts.announcesservice.entities.CategoryFieldCheck;
import org.mts.announcesservice.service.IAnnounceTypeService;
import org.mts.announcesservice.service.ICategoryFieldService;
import org.mts.announcesservice.utilities.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("categoryFields")
public class CategoryFieldController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IAnnounceTypeService announceTypeService;
    @Autowired
    private ICategoryFieldService categoryFieldService;


    @PostMapping("/{id}")
    public CategoryFieldOutputDTO create(@PathVariable String id, @RequestBody CategoryFieldObjectInputDTO dto){
        CategoryField categoryField;
        switch (dto.getType()){
            case CHECKBOX, RADIO -> {
                categoryField = this.modelMapper.map(dto, CategoryFieldCheck.class);
            }
            case TEXT, SHORT_TEXT, BOOLEAN -> {
                categoryField = this.modelMapper.map(dto, CategoryField.class);
            }
            default -> {
                throw new IllegalArgumentException("Field type error");
            }
        }

        AnnounceType announceType = this.announceTypeService.getByID(id);
        announceType.addCategoryField(categoryField);
        return  this.modelMapper.map(this.categoryFieldService.create(categoryField), CategoryFieldOutputDTO.class);
    }


    @GetMapping("/{id}")
    public CategoryFieldOutputDTO getById(@PathVariable Long id){
        return this.modelMapper.map(this.categoryFieldService.getByID(id), CategoryFieldOutputDTO.class);
    }


    @PutMapping("/{id}")
    public CategoryFieldOutputDTO update(@PathVariable @NotEmpty Long id, @RequestBody CategoryFieldInputDTO dto){
        CategoryField type = this.modelMapper.map(dto, CategoryField.class);
        type.setId(id);
        return this.modelMapper.map(this.categoryFieldService.update(type), CategoryFieldOutputDTO.class);
    }


    @DeleteMapping("/{id}")
    public  CategoryFieldOutputDTO delete(@PathVariable Long id){
        return this.modelMapper.map(this.categoryFieldService.deleteById(id), CategoryFieldOutputDTO.class);
    }

    @GetMapping
    public Map<String, Object> getAll(@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") int page){

        Page<CategoryField> fields = this.categoryFieldService.getCategoryFields(PageRequest.of(page, size));
        Map<String, Object> map = WebUtils.pageToMap(fields);
        map.put("content", fields.getContent().stream().map(c->this.modelMapper.map(c, CategoryFieldOutputDTO.class)).toList());
        return map;

    }
}
