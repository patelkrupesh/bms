package com.bms.controllers;

import com.bms.dto.MovieDto;
import com.bms.dto.ShowDto;
import com.bms.dto.ShowInputDto;
import com.bms.services.MovieService;
import com.bms.services.ShowService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/shows")
@Api(value = "Show Management")
public class ShowController {
    @Autowired
    private ShowService showService;

    @PostMapping("add")
    public ResponseEntity<Boolean> addShow(@RequestBody ShowInputDto showInputDto) {
        boolean result = false;
        ShowDto savedDto = null;
        try{
            savedDto = showService.addShow(showInputDto);
        }catch (Exception e) {
            System.out.println("Got exception while adding show : " + e.getMessage());
            return ResponseEntity.badRequest().body(false);
        }
        if(savedDto != null){
            result = true;
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("edit")
    public ResponseEntity<ShowDto> editShow(@RequestBody ShowDto showDto) {
        boolean result = false;
        ShowDto savedDto = null;
        try{
            savedDto = showService.editShow(showDto);
        }catch (Exception e) {
            System.out.println("Got exception while editing show : " + e.getMessage());
            return ResponseEntity.badRequest().body(savedDto);
        }
        return ResponseEntity.ok(savedDto);
    }

    @GetMapping("allshows")
    public ResponseEntity<List<ShowDto>> getAllShows(
            @RequestParam(name = "movieName", required = true) String movieName,
            @RequestParam(name = "city", required = true) String city,
            @RequestParam(name = "showDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate showDate) {
        return  ResponseEntity.ok(showService.searchShow(movieName, city, showDate));
    }
}