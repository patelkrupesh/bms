package com.bms.services;

import com.bms.adaptors.UserAdapter;
import com.bms.dto.UserDto;
import com.bms.exceptions.DuplicateRecordException;
import com.bms.model.UserEntity;
import com.bms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

//    @Autowired
    private UserRepository userRepository;

    public boolean userSignUp(UserDto userDto) throws Exception{
        boolean result = false;

        UserEntity dbData = userRepository.findUserByUsername(userDto.getUsername());
        if (dbData != null && dbData.getUsername() == userDto.getUsername()) {
            throw new DuplicateRecordException("User Name Already Exists: " + userDto.getName());
        }

        UserEntity userEntity = UserAdapter.toEntity(userDto);
        userEntity = userRepository.save(userEntity);

        return true;
    }

    public boolean editSignUp(UserDto userDto) throws Exception {
        boolean result = false;
        UserEntity dbData = userRepository.findUserByUsername(userDto.getUsername());
        if (dbData == null || dbData.getUsername() == null) {
            throw new Exception("User not found " + userDto.getName());
        }

        UserEntity userEntity = UserAdapter.toEntity(userDto);
        userEntity = userRepository.save(userEntity);

        return false;
    }

    public UserDto login(String username, String password) throws Exception {
        boolean result = false;
        UserEntity userEntity = userRepository.findUserByCredentials(username, password);

        if (userEntity == null) {
            throw new Exception("Username not found : " + username);
        }

        return UserAdapter.toDto(userEntity);
    }
}