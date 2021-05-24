package com.bms.dto;

import com.bms.model.BookingEntity;
import com.bms.model.ScreenEntity;
import com.bms.model.SeatsEntity;
import com.bms.model.TheaterEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class ShowDto {
    private long id;
    private LocalDate releaseDate;
    private int time;
//    private List<Long> bookingDtosId;
//    private List<Long> seatsDtosId;
    private ScreenDto screenDto;
}