package com.bms.dto;

import com.bms.model.BookingEntity;
import com.bms.model.SeatsEntity;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class BookingEntities {
    private BookingEntity bookingEntity;
    private List<SeatsEntity> seatsEntityList;
}