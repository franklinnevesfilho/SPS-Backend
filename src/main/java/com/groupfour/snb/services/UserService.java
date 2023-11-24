package com.groupfour.snb.services;

import com.groupfour.snb.models.user.Role;
import com.groupfour.snb.models.user.User;
import com.groupfour.snb.repositories.UserRepository;
import com.groupfour.snb.utils.responses.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * <h1>User Service</h1>
 * This Service will perform actions by the User
 * @author Franklin Neves Filho
 */
@RequiredArgsConstructor
@Service
public class UserService extends MainService  {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public Response add(User user){
        userRepository.save(user);
        return Response.builder()
                .node(mapToJson(user))
                .build();
    }
    public Response getById(String id){
        List<String> errors = new LinkedList<>();
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            errors.add("User was not found");
        }
        return Response.builder()
                .node(mapToJson(user))
                .errors(errors)
                .build();
    }
    public Response getAll() {
        return Response.builder()
                .node(mapToJson(userRepository.findAll()))
                .build();
    }

    public Response getByEmail(String email) {
        List<String> errors = new LinkedList<>();

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            errors.add("User was not found");
        }

        return Response.builder()
                .node(mapToJson(user))
                .errors(errors)
                .build();
    }

    public Response sellerRequest(String userId, String licenseNum){
        List<String> errors = new LinkedList<>();
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = null;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            Role role = roleService.getRoleByAuthority("SELLER");
            user.getAuthorities().add(role);
            user.setLicense(licenseNum);
            userRepository.save(user);
        }else{
            errors.add("User not found");
        }

        return Response.builder().node(mapToJson(user)).errors(errors).build();
    }
}
