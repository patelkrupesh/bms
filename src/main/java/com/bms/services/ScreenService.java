package com.bms.services;

import com.bms.adaptors.ScreenAdapter;
import com.bms.dto.ScreenDto;
import com.bms.model.ScreenEntity;
import com.bms.repositories.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScreenService {

    @Autowired
    private ScreenRepository screenRepository;

    public ScreenDto addScreen(ScreenDto screenDto) throws Exception {
        ScreenEntity dbData = screenRepository.save(ScreenAdapter.toEntity(screenDto));
        if (dbData == null) {
            throw new Exception("Unable to add screen " + screenDto.getNoOfSeats());
        }
        return ScreenAdapter.toDto(dbData);
    }

    public ScreenDto editScreen(ScreenDto screenDto) throws Exception {
        Optional<ScreenEntity> optionalDbData = screenRepository.findById(screenDto.getId());
        if (!optionalDbData.isPresent()) {
            throw new Exception("Could not find the screen with id : " + screenDto.getId());
        }
        ScreenEntity dbData = screenRepository.save(ScreenAdapter.toEntity(screenDto));
        if (dbData == null) {
            throw new Exception("Could not edit screen data : " + screenDto.getId());
        }
        return ScreenAdapter.toDto(dbData);
    }

}