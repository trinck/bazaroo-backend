package org.mts.usersservice.web;


import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.mts.usersservice.dtos.RoleInputDTO;
import org.mts.usersservice.dtos.RoleOutputDTO;
import org.mts.usersservice.entities.Role;
import org.mts.usersservice.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("roles")
public class RoleController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IRoleService roleService;

    @PostMapping
    public RoleOutputDTO createRole(@RequestBody RoleInputDTO roleInputDTO){
        Role role = this.modelMapper.map(roleInputDTO, Role.class);
        return this.modelMapper.map(this.roleService.creatRole(role), RoleOutputDTO.class);
    }


    @PutMapping("/{id}")
    public RoleOutputDTO updateRole(@PathVariable @NotNull Long id,@RequestBody RoleInputDTO roleInputDTO){
        Role role = this.modelMapper.map(roleInputDTO, Role.class);
        return this.modelMapper.map(this.roleService.updateRole(role), RoleOutputDTO.class);
    }


    @DeleteMapping("/{id}")
    public RoleOutputDTO deleteRole(@PathVariable @NotNull Long id){
        return this.modelMapper.map(this.roleService.deleteRoleById(id), RoleOutputDTO.class);
    }

    @GetMapping
    public List<RoleOutputDTO> getRoles(){
        return this.roleService.getListRoles().stream().map(r->this.modelMapper.map(r,RoleOutputDTO.class)).toList();
    }


    @GetMapping("/{id}")
    public RoleOutputDTO getRole(@PathVariable @NotNull Long id){
        return this.modelMapper.map(this.roleService.getRoleById(id), RoleOutputDTO.class);
    }


}
