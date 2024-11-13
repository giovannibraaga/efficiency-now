package com.efficiencynow.efficiencynow.utils;

import com.efficiencynow.efficiencynow.dtos.UserDTO;
import com.efficiencynow.efficiencynow.entities.UserEntity;

/**
 * Classe utilitária para conversão entre UserEntity e UserDTO.
 */

public class Populator {

    /**
     * Converte uma entidade UserEntity em um objeto UserDTO.
     *
     * @param userEntity A entidade UserEntity a ser convertida.
     * @return O objeto UserDTO resultante da conversão.
     */
    public static UserDTO toModel(UserEntity userEntity) {
        return new UserDTO(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getPassword()
        );
    }

    /**
     * Converte um objeto UserDTO em uma entidade UserEntity.
     *
     * @param userDTO O objeto UserDTO a ser convertido.
     * @return A entidade UserEntity resultante da conversão.
     */
    public static UserEntity toEntity(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }

}
