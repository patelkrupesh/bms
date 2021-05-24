package com.bms.dto;

import com.bms.enums.UserType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class UserDto {
    private String username;
    private String password;
    private UserType type;
    private String name;
    private String address;
}