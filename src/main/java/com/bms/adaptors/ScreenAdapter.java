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
				.theaterEntity(TheatreAdapter.toEntity(screenDto.getTheatreDto()))
				.build();

	}

	public static ScreenDto toDto(ScreenEntity screenEntity) {

		return ScreenDto.builder()
				.id(screenEntity.getId())
				.screenname(screenEntity.getScreenname())
				.noOfSeats(screenEntity.getNoOfSeats())
				.theatreDto(TheatreAdapter.toDto(screenEntity.getTheaterEntity()))
				.build();
	}

}