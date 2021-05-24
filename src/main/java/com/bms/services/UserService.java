package com.bms.services;

import com.bms.adaptors.BookingAdapter;
import com.bms.adaptors.UserAdapter;
import com.bms.dto.BookingDto;
import com.bms.dto.UserDto;
import com.bms.exceptions.DuplicateRecordException;
import com.bms.model.BookingEntity;
import com.bms.model.UserEntity;
import com.bms.repositories.BookingRepository;
import com.bms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

//    @Autowired
    private UserRepository userRepository;
//    @Autowired
    private BookingRepository bookingRepository;

    public UserDto userSignUp(UserDto userDto) throws Exception{

        UserEntity dbData = userRepository.findUserByUsername(userDto.getUsername());
        if (dbData != null && dbData.getUsername() == userDto.getUsername()) {
            throw new DuplicateRecordException("User Name Already Exists: " + userDto.getName());
        }

        UserEntity userEntity = UserAdapter.toEntity(userDto);
        userEntity = userRepository.save(userEntity);

        return UserAdapter.toDto(userEntity);
    }

    public UserDto editSignUp(UserDto userDto) throws Exception {

        UserEntity dbData = userRepository.findUserByUsername(userDto.getUsername());
        if (dbData == null || dbData.getUsername() == null) {
            throw new Exception("User not found " + userDto.getName());
        }

        UserEntity userEntity = UserAdapter.toEntity(userDto);
        userEntity = userRepository.save(userEntity);

        return UserAdapter.toDto(userEntity);
    }

    public UserDto login(String username, String password) throws Exception {

        UserEntity userEntity = userRepository.findUserByCredentials(username, password);

        if (userEntity == null) {
            throw new Exception("Username not found : " + username);
        }

        return UserAdapter.toDto(userEntity);
    }

    public List<BookingDto> getAllBookingData(String username){
        List<BookingDto> bookingDtos = new ArrayList<>();
        Iterable<BookingEntity> bookingEntities = bookingRepository.findBookingEntitiesByUser(username);
        bookingEntities.forEach(booking -> bookingDtos.add(BookingAdapter.toDto(booking)));
        return bookingDtos;
    }
}