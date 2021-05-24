package com.bms.dto;

import com.bms.adaptors.MovieAdapter;
import com.bms.adaptors.ScreenAdapter;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class ShowInputDto {
    private long id;
    private LocalDate date;
    private int time;
    private ScreenDto screen;
    private MovieDto movie;
    private List<SeatInputDto> seatInput;
}