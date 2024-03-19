package com.ecommerce.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.ecommerce.enums.Role;
import com.ecommerce.models.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomOAuth2User implements OAuth2User {

    private OAuth2User oauth2User;

    public CustomOAuth2User(OAuth2User oauth2User) {
        this.oauth2User = oauth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = getUserRoles();

        if (roles != null && !roles.isEmpty()) {
            // Convert roles to GrantedAuthority objects
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                    .collect(Collectors.toList());
        } else {
            // Provide a default role if user roles are not available
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getName() {
        return oauth2User.getAttribute("name");
    }

    // Helper method to retrieve user roles from OAuth2User attributes
    private Set<Role> getUserRoles() {
        // Assuming roles are stored as an attribute with key "roles" in OAuth2User attributes
        Collection<String> roleStrings = (Collection<String>) oauth2User.getAttribute("roles");
        return roleStrings != null ?
                roleStrings.stream()
                        .map(Role::valueOf) // Convert role string to Role enum
                        .collect(Collectors.toSet()) :
                Collections.emptySet();
    }

    public String getEmail() {
        return oauth2User.<String>getAttribute("email");
    }
}
