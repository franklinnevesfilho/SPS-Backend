package com.groupfour.snb.services;

import com.groupfour.snb.models.Role;
import com.groupfour.snb.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleService {
    RoleRepository roleRepository;

    public Role addRole(Role role){
        roleRepository.save(role);
        return role;
    }
    public Iterable<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public Role getRoleByAuthority(String user) {
        return roleRepository.findByAuthority(user).orElseThrow();
    }
}
