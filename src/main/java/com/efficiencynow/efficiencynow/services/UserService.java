package com.efficiencynow.efficiencynow.services;

import com.efficiencynow.efficiencynow.dtos.UserDTO;
import com.efficiencynow.efficiencynow.entities.UserEntity;
import com.efficiencynow.efficiencynow.repositories.UserRepository;
import com.efficiencynow.efficiencynow.utils.Populator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {
        UserEntity userEntity = Populator.toEntity(userDTO);

        UserEntity savedUser = userRepository.save(userEntity);
        return Populator.toModel(savedUser);
    }

    public Optional<UserDTO> findByEmail(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        return userEntity.map(Populator::toModel);
    }
}
