package com.bms.dto;

import com.bms.enums.SeatCategory;
import com.bms.model.BookingEntity;
import com.bms.model.ScreenEntity;
import com.bms.model.ShowEntity;
import com.bms.model.TheaterEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class SeatsDto {
    private long id;
    private int seatNumber;
    private int rate;
    private SeatCategory seatType;
    private boolean booked;
    private ShowDto show;
    private BookingDto booking;
//    private ShowDto show;
}