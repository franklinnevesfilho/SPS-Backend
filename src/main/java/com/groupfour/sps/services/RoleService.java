package com.groupfour.sps.services;

import com.groupfour.sps.models.user.Role;
import com.groupfour.sps.repositories.RoleRepository;
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

    public boolean isAuthorityPresent(String authority) {
        return roleRepository.findByAuthority(authority).isPresent();
    }
}
