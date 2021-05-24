package com.bms.adaptors;

import com.bms.dto.SeatsDto;
import com.bms.model.SeatsEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SeatsAdapter {

	public static SeatsEntity toEntity(SeatsDto seatsDto) {

		return SeatsEntity.builder()
				.id(seatsDto.getId())
				.seatNumber(seatsDto.getSeatNumber())
				.rate(seatsDto.getRate())
				.seatType(seatsDto.getSeatType())
				.booked(seatsDto.isBooked())
				.screenEntity(ScreenAdapter.toEntity(seatsDto.getScreenDto()))
				.bookingEntity(BookingAdapter.toEntity(seatsDto.getBookingDto()))
				.showEntity(ShowAdapter.toEntity(seatsDto.getShowDto()))
				.build();

	}

	public static SeatsDto toDto(SeatsEntity seatsEntity) {

		return SeatsDto.builder()
				.id(seatsEntity.getId())
				.seatNumber(seatsEntity.getSeatNumber())
				.rate(seatsEntity.getRate())
				.seatType(seatsEntity.getSeatType())
				.booked(seatsEntity.isBooked())
				.screenDto(ScreenAdapter.toDto(seatsEntity.getScreenEntity()))
				.bookingDto(BookingAdapter.toDto(seatsEntity.getBookingEntity()))
				.showDto(ShowAdapter.toDto(seatsEntity.getShowEntity()))
				.build();
	}

}