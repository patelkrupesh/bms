package com.bms.controllers;

import com.bms.dto.MovieDto;
import com.bms.services.MovieService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/movies")
@Api(value = "Movie Management")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping("add")
    public ResponseEntity<Boolean> addMovie(@RequestBody MovieDto movieDto) {
        boolean result = false;
        MovieDto savedDto = null;
        try{
            savedDto = movieService.addMovie(movieDto);
        }catch (Exception e) {
            System.out.println("Got exception while adding movie : " + e.getMessage());
            return ResponseEntity.badRequest().body(false);
        }
        if(savedDto != null){
            result = true;
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("edit")
    public ResponseEntity<MovieDto> editMovie(@RequestBody MovieDto movieDto) {
        boolean result = false;
        MovieDto savedDto = null;
        try{
            savedDto = movieService.editMovie(movieDto);
        }catch (Exception e) {
            System.out.println("Got exception while editing movie : " + e.getMessage());
            return ResponseEntity.badRequest().body(savedDto);
        }
        return ResponseEntity.ok(savedDto);
    }

    @GetMapping("allmovies")
    public ResponseEntity<List<MovieDto>> getAllMovieList() {
        return  ResponseEntity.ok(movieService.getMovieList());
    }
}