package com.bms.adaptors;

import com.bms.dto.ScreenDto;
import com.bms.model.ScreenEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ScreenAdapter {

	public static ScreenEntity toEntity(ScreenDto screenDto) {

		return ScreenEntity.builder()
				.id(screenDto.getId())
				.screenname(screenDto.getScreenname())
				.noOfSeats(screenDto.getNoOfSeats())
				.theater(TheatreAdapter.toEntity(screenDto.getTheatre()))
				.build();

	}

	public static ScreenDto toDto(ScreenEntity screenEntity) {

		return ScreenDto.builder()
				.id(screenEntity.getId())
				.screenname(screenEntity.getScreenname())
				.noOfSeats(screenEntity.getNoOfSeats())
				.theatre(TheatreAdapter.toDto(screenEntity.getTheater()))
				.build();
	}

}