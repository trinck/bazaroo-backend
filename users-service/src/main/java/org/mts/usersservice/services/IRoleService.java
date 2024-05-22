package org.mts.usersservice.services;

import org.mts.usersservice.entities.Role;
import org.mts.usersservice.enums.EnumRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRoleService {


    public Role creatRole(Role role);
    public Role getRoleById(Long id);
    public Role updateRole(Role role);
    public Role deleteRoleById(Long id);
    public Page<Role> getRoles(Pageable pageable);
    public List<Role> getListRoles();
   public Role getRoleByRoleType(EnumRole role);
}
