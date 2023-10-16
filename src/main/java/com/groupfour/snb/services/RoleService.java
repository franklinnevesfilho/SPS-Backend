package com.groupfour.snb.services;

import com.groupfour.snb.models.user.Role;
import com.groupfour.snb.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;
    public Role addRole(Role role){
        roleRepository.save(role);
        return role;
    }
    public Role getRoleByAuthority(String user) {
        return roleRepository.findByAuthority(user).orElseThrow();
    }
}
