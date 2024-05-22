package org.mts.usersservice.web;

import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.mts.usersservice.dtos.ContactInputDTO;
import org.mts.usersservice.dtos.ContactOutputDTO;
import org.mts.usersservice.entities.Contact;
import org.mts.usersservice.entities.User;
import org.mts.usersservice.services.IContactService;
import org.mts.usersservice.services.IUserService;
import org.mts.usersservice.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("contacts")
public class ContactController {

    @Autowired
    private IContactService contactService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("{id}")
    public ContactOutputDTO createContact(@PathVariable String id, @RequestBody ContactInputDTO contactInputDTO){

        User user = this.userService.getUserById(id);
        Contact contact = this.modelMapper.map(contactInputDTO,Contact.class);
        contact.setUser(user);
        contact = this.contactService.creatContact(contact);
        return  this.modelMapper.map(contact,ContactOutputDTO.class);
    }

    @GetMapping("/{id}")
    public ContactOutputDTO getContact(@PathVariable Long id){
        return this.modelMapper.map(this.contactService.getContactById(id), ContactOutputDTO.class);
    }

    @PutMapping("/{id}")
    public ContactOutputDTO updateContact(@PathVariable @NotNull Long id, @RequestBody ContactInputDTO dto){
        Contact contact = this.modelMapper.map(dto, Contact.class);
        return this.modelMapper.map(this.contactService.updateContact(contact), ContactOutputDTO.class);
    }

    @DeleteMapping("/{id}")
    public ContactOutputDTO deleteContact(@PathVariable Long id){
        return this.modelMapper.map(this.contactService.deleteContactById(id), ContactOutputDTO.class);
    }

    @GetMapping("/")
    public Map<String,Object> getContacts(@RequestParam(defaultValue = "8") int size,@RequestParam(defaultValue = "0") int page){

        Page<Contact> contacts = this.contactService.getContacts(PageRequest.of(page,size));
        Map<String,Object> dtos = WebUtils.pageToMap(contacts);
        dtos.put("content", contacts.getContent().stream().map(c -> this.modelMapper.map(c, ContactOutputDTO.class)).toList());
        return dtos;
    }

}
