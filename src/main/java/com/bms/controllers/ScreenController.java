package com.bms.controllers;

import com.bms.dto.MovieDto;
import com.bms.dto.ScreenDto;
import com.bms.services.MovieService;
import com.bms.services.ScreenService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/screens")
@Api(value = "Screen Management")
public class ScreenController {
    @Autowired
    private ScreenService screenService;

    @PostMapping("add")
    public ResponseEntity<Boolean> addScreen(@RequestBody ScreenDto screenDto) {
        boolean result = false;
        ScreenDto savedDto = null;
        try{
            savedDto = screenService.addScreen(screenDto);
        }catch (Exception e) {
            System.out.println("Got exception while adding screen : " + e.getMessage());
            return ResponseEntity.badRequest().body(false);
        }
        if(savedDto != null){
            result = true;
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("edit")
    public ResponseEntity<ScreenDto> editScreen(@RequestBody ScreenDto screenDto) {
        boolean result = false;
        ScreenDto savedDto = null;
        try{
            savedDto = screenService.editScreen(screenDto);
        }catch (Exception e) {
            System.out.println("Got exception while editing screen : " + e.getMessage());
            return ResponseEntity.badRequest().body(savedDto);
        }
        return ResponseEntity.ok(savedDto);
    }
}