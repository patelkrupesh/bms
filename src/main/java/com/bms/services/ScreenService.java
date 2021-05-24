package com.bms.services;

import com.bms.adaptors.ScreenAdapter;
import com.bms.adaptors.TheatreAdapter;
import com.bms.dto.ScreenDto;
import com.bms.dto.TheatreDto;
import com.bms.exceptions.DuplicateRecordException;
import com.bms.model.ScreenEntity;
import com.bms.model.TheaterEntity;
import com.bms.repositories.ScreenRepository;
import com.bms.repositories.TheatreRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScreenService {

    //@Autowired
    private ScreenRepository screenRepository;

    public ScreenDto addScreen(ScreenDto screenDto) throws Exception {
        ScreenEntity dbData = screenRepository.save(ScreenAdapter.toEntity(screenDto));
        if (dbData == null) {
            throw new Exception("Unable to add screen " + screenDto.getNoOfSeats());
        }
        return ScreenAdapter.toDto(dbData);
    }

    public ScreenDto editScreen(ScreenDto screenDto) throws Exception {
        ScreenEntity dbData = screenRepository.findOne(screenDto.getId());
        if (dbData == null) {
            throw new Exception("Could not find the screen with id : " + screenDto.getId());
        }
        dbData = screenRepository.save(ScreenAdapter.toEntity(screenDto));
        if (dbData == null) {
            throw new Exception("Could not edit screen data : " + screenDto.getId());
        }
        return ScreenAdapter.toDto(dbData);
    }

}