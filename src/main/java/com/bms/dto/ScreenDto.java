package com.bms.dto;

import com.bms.enums.Language;
import com.bms.enums.MovieGenre;
import com.bms.model.TheaterEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class ScreenDto {
    private long id;
    private String screenname;
    private String noOfSeats;
    private TheatreDto theatre;
}