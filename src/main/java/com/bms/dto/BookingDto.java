package com.bms.dto;

import com.bms.enums.Language;
import com.bms.enums.MovieGenre;
import com.bms.model.PaymentEntity;
import com.bms.model.SeatsEntity;
import com.bms.model.ShowEntity;
import com.bms.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class BookingDto {
    private long id;
    private Date bookingtime;
    private String allottedSeats;
    private double amount;
//    private List<Long> seatsId;
//    private List<Long> paymentDtosId;
    private String ticketDownloadlink;
    private ShowDto showDto;
    private UserDto userDto;
}