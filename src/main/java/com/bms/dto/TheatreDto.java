package com.bms.dto;

import com.bms.enums.UserType;
import com.bms.model.ScreenEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class TheatreDto {
    private long id;
    private String username;
    private String password;
    private String name;
    private String address;
    private String city;
//    private List<Long> screenDtosId;
}