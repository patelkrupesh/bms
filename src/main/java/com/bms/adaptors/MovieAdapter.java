package com.bms.adaptors;

import com.bms.dto.MovieDto;
import com.bms.dto.MovieDto;
import com.bms.model.MovieEntity;
import com.bms.model.MovieEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MovieAdapter {

	public static MovieEntity toEntity(MovieDto movieDto) {

		return MovieEntity.builder()
				.id(movieDto.getId())
				.name(movieDto.getName())
				.language(movieDto.getLanguage())
				.length(movieDto.getLength())
				.starcast(movieDto.getStarcast())
				.genre(movieDto.getGenre())
				.releaseDate(movieDto.getReleaseDate())
				.build();

	}

	public static MovieDto toDto(MovieEntity MovieEntity) {

		return MovieDto.builder()
				.id(MovieEntity.getId())
				.name(MovieEntity.getName())
				.language(MovieEntity.getLanguage())
				.length(MovieEntity.getLength())
				.starcast(MovieEntity.getStarcast())
				.genre(MovieEntity.getGenre())
				.releaseDate(MovieEntity.getReleaseDate())
				.build();
	}

}