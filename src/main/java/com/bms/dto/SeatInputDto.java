package com.bms.dto;

import com.bms.enums.SeatCategory;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class SeatInputDto {
    private SeatCategory seatType;
    private int noOfSeats;
    private int rate;
}