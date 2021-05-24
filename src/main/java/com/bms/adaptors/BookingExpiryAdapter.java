package com.bms.adaptors;

import com.bms.dto.BookingExpiryDto;
import com.bms.model.BookingExpiryEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookingExpiryAdapter {

	public static BookingExpiryEntity toEntity(BookingExpiryDto bookingExpiryDto) {

		return BookingExpiryEntity.builder()
				.id(bookingExpiryDto.getId())
				.expirytime(bookingExpiryDto.getExpirytime())
				.booking(bookingExpiryDto.getBooking())
				.build();

	}

	public static BookingExpiryDto toDto(BookingExpiryEntity bookingExpiryEntity) {

		return BookingExpiryDto.builder()
				.id(bookingExpiryEntity.getId())
				.expirytime(bookingExpiryEntity.getExpirytime())
				.booking(bookingExpiryEntity.getBooking())
				.build();
	}

}