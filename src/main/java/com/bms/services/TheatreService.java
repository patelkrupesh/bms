package com.bms.services;

import com.bms.adaptors.TheatreAdapter;
import com.bms.adaptors.UserAdapter;
import com.bms.dto.TheatreDto;
import com.bms.dto.UserDto;
import com.bms.exceptions.DuplicateRecordException;
import com.bms.model.TheaterEntity;
import com.bms.model.UserEntity;
import com.bms.repositories.TheatreRepository;
import com.bms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TheatreService {

//        @Autowired
        private TheatreRepository theatreRepository;

        public TheatreDto theatreSignUp(TheatreDto theatreDto) throws Exception{

            TheaterEntity dbData = theatreRepository.findTheaterEntitiesByUsername(theatreDto.getUsername());
            if (dbData != null && dbData.getUsername() == theatreDto.getUsername()) {
                throw new DuplicateRecordException("Theatre username already exists: " + theatreDto.getName());
            }

            TheaterEntity theaterEntity = TheatreAdapter.toEntity(theatreDto);
            theaterEntity = theatreRepository.save(theaterEntity);

            return TheatreAdapter.toDto(theaterEntity);
        }

        public TheatreDto editTheatreData(TheatreDto theatreDto) throws Exception {

            TheaterEntity dbData = theatreRepository.findTheaterEntitiesByUsername(theatreDto.getUsername());
            if (dbData == null || dbData.getUsername() == null) {
                throw new Exception("User not found " + theatreDto.getName());
            }

            TheaterEntity theaterEntity = TheatreAdapter.toEntity(theatreDto);
            theaterEntity = theatreRepository.save(theaterEntity);
            return TheatreAdapter.toDto(theaterEntity);
        }

        public TheatreDto theatreLogin(String username, String password) throws Exception {
            TheaterEntity theaterEntity = theatreRepository.findTheaterEntitiesByCredentials(username, password);
            if (theaterEntity == null) {
                throw new Exception("Username not found : " + username);
            }
            return TheatreAdapter.toDto(theaterEntity);
        }

        public List<TheatreDto> getTheatreInCity(String city) throws Exception {
            List<TheaterEntity> theaterEntity = new ArrayList<TheaterEntity>();
            List<TheatreDto> returnDto = new ArrayList<TheatreDto>();
            try {
                theatreRepository.findTheaterEntitiesByCity(city).forEach(theatre -> theaterEntity.add(theatre));
            } catch (Exception e) {
                throw new Exception("Could not get the theatres in city :" + city);
            }
            if (theaterEntity.size() == 0) {
                throw new Exception("No theatre found in city " + city + ".");
            }
            theaterEntity.forEach(entity ->returnDto.add(TheatreAdapter.toDto(entity)));
            return returnDto;
        }
}