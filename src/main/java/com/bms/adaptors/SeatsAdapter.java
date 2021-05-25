package com.bms.adaptors;

import com.bms.dto.ScreenDto;
import com.bms.dto.SeatInputDto;
import com.bms.dto.SeatsDto;
import com.bms.model.SeatsEntity;
import com.bms.model.ShowEntity;
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
				.show(ShowAdapter.toEntity(seatsDto.getShow()))
				.booking(BookingAdapter.toEntity(seatsDto.getBooking()))
				.build();

	}

	public static SeatsDto toDto(SeatsEntity seatsEntity) {

		return SeatsDto.builder()
				.id(seatsEntity.getId())
				.seatNumber(seatsEntity.getSeatNumber())
				.rate(seatsEntity.getRate())
				.seatType(seatsEntity.getSeatType())
				.booked(seatsEntity.isBooked())
				.show(ShowAdapter.toDto(seatsEntity.getShow()))
				.booking(BookingAdapter.toDto(seatsEntity.getBooking()))
				.build();
	}

	public static SeatsEntity toEntityFromInput(SeatInputDto seatInputDto, int seatNo,
												ScreenDto screenDto, ShowEntity showEntity){
		return SeatsEntity.builder()
				.seatNumber(seatNo)
				.rate(seatInputDto.getRate())
				.seatType(seatInputDto.getSeatType())
				.booked(false)
				.screen(ScreenAdapter.toEntity(screenDto))
				.show(showEntity)
				.build();
	}
}