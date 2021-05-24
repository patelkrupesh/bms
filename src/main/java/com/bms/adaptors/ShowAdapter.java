package com.bms.adaptors;

import com.bms.dto.ShowDto;
import com.bms.model.ShowEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ShowAdapter {

	public static ShowEntity toEntity(ShowDto showDto) {

		return ShowEntity.builder()
				.id(showDto.getId())
				.releaseDate(showDto.getReleaseDate())
				.time(showDto.getTime())
				.screenEntity(ScreenAdapter.toEntity(showDto.getScreenDto()))
				.build();

	}

	public static ShowDto toDto(ShowEntity showEntity) {

		return ShowDto.builder()
				.id(showEntity.getId())
				.releaseDate(showEntity.getReleaseDate())
				.time(showEntity.getTime())
				.screenDto(ScreenAdapter.toDto(showEntity.getScreenEntity()))
				.build();
	}

}