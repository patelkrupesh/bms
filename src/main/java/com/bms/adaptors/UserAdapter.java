package com.bms.adaptors;

import com.bms.dto.UserDto;
import com.bms.enums.UserType;
import com.bms.model.UserEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserAdapter {

	public static UserEntity toEntity(UserDto userDto) {

		return UserEntity.builder()
				.username(userDto.getUsername())
				.password(userDto.getPassword())
				.type(userDto.getType().getName())
				.name(userDto.getName())
				.address(userDto.getAddress())
				.build();

	}

	public static UserDto toDto(UserEntity userEntity) {

		return UserDto.builder()
				.username(userEntity.getUsername())
				.password(userEntity.getPassword())
				.name(userEntity.getName())
				.type(UserType.getEnum(userEntity.getType()))
				.address(userEntity.getAddress())
				.build();
	}

}