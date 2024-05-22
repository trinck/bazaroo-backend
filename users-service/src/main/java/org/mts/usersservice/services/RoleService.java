package org.mts.usersservice.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.usersservice.entities.Role;
import org.mts.usersservice.enums.EnumRole;
import org.mts.usersservice.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleService implements IRoleService{

   @Autowired
   private RoleRepository roleRepository;

    /**
     * @param role
     * @return
     */
    @Override
    public Role creatRole(Role role) {
        return this.roleRepository.save(role);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Role getRoleById(Long id) {
        return this.roleRepository.findById(id).orElseThrow();
    }

    /**
     * @param role
     * @return
     */
    @Override
    public Role updateRole(Role role) {
        return this.roleRepository.save(role);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Role deleteRoleById(Long id) {

        Role role = this.roleRepository.findById(id).orElseThrow();
        this.roleRepository.deleteById(id);
        return role;
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Role> getRoles(Pageable pageable) {
        return this.roleRepository.findAll(pageable);
    }

    /**
     * @return
     */
    @Override
    public List<Role> getListRoles() {
        return this.roleRepository.findAll();
    }

    /**
     * @param role
     * @return
     */
    @Override
    public Role getRoleByRoleType(EnumRole role) {
        return this.roleRepository.findRoleByRole(role).orElseThrow();
    }
}
