package com.bms.controllers;

import com.bms.dto.TheatreDto;
import com.bms.dto.UserDto;
import com.bms.services.TheatreService;
import com.bms.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/theatres")
@Api(value = "Theatre Management")
public class TheatreController{
    @Autowired
    private TheatreService theatreService;

    @PostMapping("signUp")
    public ResponseEntity<Boolean> theatreSignUp(@RequestBody TheatreDto theatreDto) {
        boolean result = false;
        TheatreDto savedDto = null;
        try{
            savedDto = theatreService.theatreSignUp(theatreDto);
        }catch (Exception e) {
            System.out.println("Got exception while adding theatre : " + e.getMessage());
            return ResponseEntity.badRequest().body(false);
        }
        if(savedDto != null){
            result = true;
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("edit")
    public ResponseEntity<TheatreDto> editTheatre(@RequestBody TheatreDto theatreDto) {
        boolean result = false;
        TheatreDto savedDto = null;
        try{
            savedDto = theatreService.editTheatreData(theatreDto);
        }catch (Exception e) {
            System.out.println("Got exception while editing theatre : " + e.getMessage());
            return ResponseEntity.badRequest().body(savedDto);
        }
        return ResponseEntity.ok(savedDto);
    }

    @GetMapping("login")
    public ResponseEntity<TheatreDto> login(@RequestParam(name = "username", required = true) String username,
                                         @RequestParam(name = "password", required = true) String password) {
        TheatreDto result = null;
        try{
            result = theatreService.theatreLogin(username, password);
        }catch (Exception e) {
            System.out.println("Got exception while logging in as theatre : " + e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("theatreByCity")
    public ResponseEntity<List<TheatreDto>> getTheatreByCity(@RequestParam(name = "city", required = true) String city) {
        List<TheatreDto> result = new ArrayList<>();
        try{
            result = theatreService.getTheatreInCity(city);
        }catch (Exception e) {
            System.out.println("Got exception while fetching theatres : " + e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}