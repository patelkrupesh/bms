package com.bms.adaptors;

import com.bms.dto.TheatreDto;
import com.bms.dto.UserDto;
import com.bms.enums.UserType;
import com.bms.model.ScreenEntity;
import com.bms.model.TheaterEntity;
import com.bms.model.UserEntity;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class TheatreAdapter {

	public static TheaterEntity toEntity(TheatreDto theatreDto) {

		return TheaterEntity.builder()
				.id(theatreDto.getId())
				.username(theatreDto.getUsername())
				.password(theatreDto.getPassword())
				.name(theatreDto.getName())
				.address(theatreDto.getAddress())
				.city(theatreDto.getCity())
				.build();
	}

	public static TheatreDto toDto(TheaterEntity theaterEntity) {

		return TheatreDto.builder()
				.id(theaterEntity.getId())
				.username(theaterEntity.getUsername())
				.password(theaterEntity.getPassword())
				.name(theaterEntity.getName())
				.address(theaterEntity.getAddress())
				.city(theaterEntity.getCity())
				.build();
	}

}