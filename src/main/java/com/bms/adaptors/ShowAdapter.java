package com.bms.adaptors;

import com.bms.dto.ShowDto;
import com.bms.dto.ShowInputDto;
import com.bms.model.MovieEntity;
import com.bms.model.ShowEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ShowAdapter {

	public static ShowEntity toEntity(ShowDto showDto) {

		return ShowEntity.builder()
				.id(showDto.getId())
				.date(showDto.getDate())
				.time(showDto.getTime())
				.screen(ScreenAdapter.toEntity(showDto.getScreen()))
				.movie(MovieAdapter.toEntity(showDto.getMovie()))
				.build();

	}

	public static ShowDto toDto(ShowEntity showEntity) {

		return ShowDto.builder()
				.id(showEntity.getId())
				.date(showEntity.getDate())
				.time(showEntity.getTime())
				.screen(ScreenAdapter.toDto(showEntity.getScreen()))
				.movie(MovieAdapter.toDto(showEntity.getMovie()))
				.build();
	}

	public static ShowDto getShowDtoFromInputDto(ShowInputDto showInputDto){
		return ShowDto.builder()
				.id(showInputDto.getId())
				.date(showInputDto.getDate())
				.time(showInputDto.getTime())
				.screen(showInputDto.getScreen())
				.movie(showInputDto.getMovie())
				.build();
	}

}