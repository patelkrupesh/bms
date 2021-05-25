package com.bms.services;

import com.bms.adaptors.SeatsAdapter;
import com.bms.adaptors.ShowAdapter;
import com.bms.adaptors.ShowUtil;
import com.bms.dto.ShowDto;
import com.bms.dto.ShowInputDto;
import com.bms.model.MovieEntity;
import com.bms.model.ScreenEntity;
import com.bms.model.SeatsEntity;
import com.bms.model.ShowEntity;
import com.bms.repositories.MovieRepository;
import com.bms.repositories.ScreenRepository;
import com.bms.repositories.SeatsRepository;
import com.bms.repositories.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private SeatsRepository seatsRepository;
    @Autowired
    private ScreenRepository screenRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public ShowDto addShow(ShowInputDto showDto) throws Exception {
        Optional<ScreenEntity> optionalScreenData = screenRepository.findById(showDto.getScreen().getId());
        if (!optionalScreenData.isPresent()) {
            throw new Exception("Unable to find screen id : " + showDto.getScreen().getId() + ", name : " +
                    showDto.getScreen().getScreenname());
        }
        Optional<MovieEntity> optionalMovieData = movieRepository.findById(showDto.getMovie().getId());
        if (!optionalMovieData.isPresent()) {
            throw new Exception("Unable to find movie id :" + showDto.getMovie().getId() + ", name : " +
                    showDto.getMovie().getName());
        }
        ShowEntity dbData = showRepository.save(ShowAdapter.toEntity(ShowAdapter.getShowDtoFromInputDto(showDto)));
        generateSeatsForShow(showDto, dbData);
        return ShowAdapter.toDto(dbData);
    }

    private void generateSeatsForShow(ShowInputDto showDto, ShowEntity showEntity) {
        List<SeatsEntity> seatsEntities = new ArrayList<>();

        for (int iterator = 1; iterator<= showDto.getSeatInput().size(); iterator++){
            seatsEntities.add(SeatsAdapter.toEntityFromInput(showDto.getSeatInput().get(iterator), iterator,
                    showDto.getScreen(), showEntity));
        }
        seatsRepository.saveAll(seatsEntities);
    }

    public ShowDto editShow(ShowDto showDto) throws Exception {
        Optional<ScreenEntity> optionalScreenData = screenRepository.findById(showDto.getScreen().getId());
        if (!optionalScreenData.isPresent()) {
            throw new Exception("Unable to find screen id : " + showDto.getScreen().getId() + ", name : " +
                    showDto.getScreen().getScreenname());
        }
        Optional<MovieEntity> optionalMovieData = movieRepository.findById(showDto.getMovie().getId());
        if (!optionalMovieData.isPresent()) {
            throw new Exception("Unable to find movie id :" + showDto.getMovie().getId() + ", name : " +
                    showDto.getMovie().getName());
        }
        Optional<ShowEntity> optionalDbData = showRepository.findById(showDto.getId());
        if (!optionalDbData.isPresent()) {
            throw new Exception("Unable to find show id :" + showDto.getId());
        }
        ShowEntity dbData = showRepository.save(ShowAdapter.toEntity(showDto));
        return ShowAdapter.toDto(dbData);
    }

    public List<ShowDto> searchShow(String movieName, String city, LocalDate showDate){

        List<ShowDto> searchResult = new ArrayList<>();

        Specification<ShowEntity> specifications = ShowUtil.createShowSpecification(movieName, city, showDate);
        Iterable<ShowEntity> showsPage = showRepository.findAll(specifications);
        showsPage.forEach(show -> searchResult.add(ShowAdapter.toDto(show)));

        return searchResult;
    }

}