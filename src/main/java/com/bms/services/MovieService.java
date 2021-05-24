package com.bms.services;

import com.bms.adaptors.MovieAdapter;
import com.bms.adaptors.ScreenAdapter;
import com.bms.adaptors.UserAdapter;
import com.bms.dto.MovieDto;
import com.bms.dto.ScreenDto;
import com.bms.dto.TheatreDto;
import com.bms.dto.UserDto;
import com.bms.exceptions.DuplicateRecordException;
import com.bms.model.MovieEntity;
import com.bms.model.ScreenEntity;
import com.bms.model.TheaterEntity;
import com.bms.model.UserEntity;
import com.bms.repositories.MovieRepository;
import com.bms.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

//        @Autowired
        private MovieRepository movieRepository;

        public MovieDto addMovie(MovieDto movieDto) throws Exception {
                MovieEntity dbData = movieRepository.save(MovieAdapter.toEntity(movieDto));
                if (dbData == null) {
                        throw new Exception("Unable to add movie " + movieDto.getName());
                }
                return MovieAdapter.toDto(dbData);
        }

        public MovieDto editMovie(MovieDto movieDto) throws Exception {
                Optional<MovieEntity> optionalDbData = movieRepository.findById(movieDto.getId());
                if (!optionalDbData.isPresent()) {
                        throw new Exception("Could not find the movie with id : " + movieDto.getId());
                }
                MovieEntity dbData = movieRepository.save(MovieAdapter.toEntity(movieDto));
                if (dbData == null) {
                        throw new Exception("Could not edit movie data : " + movieDto.getId() + ", name : " +
                                movieDto.getName());
                }
                return MovieAdapter.toDto(dbData);
        }

        public List<MovieDto> getMovieList(){
                List<MovieDto> result = new ArrayList<>();
                Iterable<MovieEntity> dbData = movieRepository.findAll();
                dbData.forEach(movie -> result.add(MovieAdapter.toDto(movie)));
                return result;
        }
}