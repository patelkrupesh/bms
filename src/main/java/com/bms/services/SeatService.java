package com.bms.services;

import com.bms.adaptors.MovieAdapter;
import com.bms.adaptors.ScreenAdapter;
import com.bms.adaptors.SeatsAdapter;
import com.bms.dto.MovieDto;
import com.bms.dto.ScreenDto;
import com.bms.dto.SeatsDto;
import com.bms.dto.ShowDto;
import com.bms.model.MovieEntity;
import com.bms.model.ScreenEntity;
import com.bms.model.SeatsEntity;
import com.bms.repositories.ScreenRepository;
import com.bms.repositories.SeatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeatService {

//    @Autowired
    private SeatsRepository seatsRepository;

    public List<SeatsDto> getAllSeats(Long showId){
        List<SeatsDto> result = new ArrayList<>();
        Iterable<SeatsEntity> dbData = seatsRepository.findSeatsByShow(showId);
        dbData.forEach(seat -> result.add(SeatsAdapter.toDto(seat)));
        return result;
    }
}