package com.ecommerce.dtos;

import com.ecommerce.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Setter
@Getter
@AllArgsConstructor
public class UserDTO {
    
    private String name;

    private String username;

    private String identifier;

    private String avatar;

    private Role role;

}
