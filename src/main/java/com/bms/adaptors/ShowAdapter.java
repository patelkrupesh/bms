package com.bms.adaptors;

import com.bms.dto.ShowDto;
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
				.screenEntity(ScreenAdapter.toEntity(showDto.getScreenDto()))
				.movieEntity(MovieAdapter.toEntity(showDto.getMovieDto()))
				.build();

	}

	public static ShowDto toDto(ShowEntity showEntity) {

		return ShowDto.builder()
				.id(showEntity.getId())
				.date(showEntity.getDate())
				.time(showEntity.getTime())
				.screenDto(ScreenAdapter.toDto(showEntity.getScreenEntity()))
				.movieDto(MovieAdapter.toDto(showEntity.getMovieEntity()))
				.build();
	}

}