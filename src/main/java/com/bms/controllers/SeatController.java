package com.bms.controllers;

import com.bms.dto.SeatsDto;
import com.bms.dto.ShowDto;
import com.bms.dto.ShowInputDto;
import com.bms.repositories.SeatsRepository;
import com.bms.services.SeatService;
import com.bms.services.ShowService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/seats")
@Api(value = "Seat Management")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @GetMapping("allseats")
    public ResponseEntity<List<SeatsDto>> getAllSeats(
            @RequestParam(name = "showId", required = true) Long showId){
        return  ResponseEntity.ok(seatService.getAllSeats(showId));
    }
}