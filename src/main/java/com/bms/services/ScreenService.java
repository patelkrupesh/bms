package com.bms.services;

import com.bms.adaptors.TheatreAdapter;
import com.bms.dto.ScreenDto;
import com.bms.dto.TheatreDto;
import com.bms.exceptions.DuplicateRecordException;
import com.bms.model.TheaterEntity;
import com.bms.repositories.ScreenRepository;
import com.bms.repositories.TheatreRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScreenService {

    //    @Autowired
    private ScreenRepository screenRepository;

    public boolean addScreen(ScreenDto screenDto) throws Exception {
        boolean result = false;
        TheaterEntity dbData = theatreRepository.findTheaterEntitiesByUsername(theatreDto.getUsername());
        if (dbData == null || dbData.getUsername() == null) {
            throw new Exception("User not found " + theatreDto.getName());
        }

        TheaterEntity userEntity = TheatreAdapter.toEntity(theatreDto);
        userEntity = theatreRepository.save(userEntity);
        return false;
    }

        public boolean editTheatreData(TheatreDto theatreDto) throws Exception {
            boolean result = false;
            TheaterEntity dbData = theatreRepository.findTheaterEntitiesByUsername(theatreDto.getUsername());
            if (dbData == null || dbData.getUsername() == null) {
                throw new Exception("User not found " + theatreDto.getName());
            }

            TheaterEntity userEntity = TheatreAdapter.toEntity(theatreDto);
            userEntity = theatreRepository.save(userEntity);
            return false;
        }



}