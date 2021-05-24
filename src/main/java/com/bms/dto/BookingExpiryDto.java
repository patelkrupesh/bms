package com.bms.dto;

import com.bms.enums.BookingStatus;
import com.bms.model.BookingEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class BookingExpiryDto {
    private long id;
    private Date expirytime;
    private BookingEntity booking;
}