package com.groupfour.snb.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

/**
 * <h1>Role</h1>
 * The class is used for defining different user roles throughout the application.
 * With this we're able to secure different parts of the application by defining
 * what roles are able to access the feature.
 *
 * @author Franklin Neves Filho
 * @Last-Modified: 09/08/2023
 */
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Integer roleId;
    @NonNull
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
