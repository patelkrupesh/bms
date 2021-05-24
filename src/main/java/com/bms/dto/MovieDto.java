package com.bms.dto;

import com.bms.enums.Language;
import com.bms.enums.MovieGenre;
import com.bms.enums.UserType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class MovieDto {
    private long id;
    private String name;
    private Language language;
    private Integer length;
    private String starcast;
    private MovieGenre genre;
    private LocalDate releaseDate;
}