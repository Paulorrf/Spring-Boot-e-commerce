package com.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dtos.UserDTO;
import com.ecommerce.mappers.UserMapper;
import com.ecommerce.models.User;
import com.ecommerce.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    public UserDTO create(UserDTO userDTO) {
        try {
            User user = userRepository.save(userMapper.userDTOtoUser(userDTO));
            return userMapper.userToUserDTO(user);
        } catch (Exception e) {
            return null;
        }
    }

}
