package com.efficiencynow.efficiencynow.utils;

import com.efficiencynow.efficiencynow.dtos.UserDTO;
import com.efficiencynow.efficiencynow.entities.UserEntity;

public class Populator {

    public UserDTO toModel(UserEntity userEntity) {
        return new UserDTO(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getPassword()
        );
    }

    public UserEntity toEntity(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }

}
