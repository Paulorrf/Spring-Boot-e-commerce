package com.ecommerce.mappers;

import com.ecommerce.dtos.UserDTO;
import com.ecommerce.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(User user);

    User userDTOtoUser(UserDTO userDTO);
}
