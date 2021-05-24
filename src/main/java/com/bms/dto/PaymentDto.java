package com.bms.dto;

import com.bms.enums.Language;
import com.bms.enums.MovieGenre;
import com.bms.enums.PaymentStatus;
import com.bms.model.BookingEntity;
import com.bms.model.UserEntity;
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
public class PaymentDto {
    private long id;
    private Integer name;
    private PaymentStatus status;
    private String gateway;
//    private Long userDtoId;
    private BookingDto bookingDto;
}