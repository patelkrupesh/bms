package com.bms.adaptors;

import com.bms.dto.BookingDto;
import com.bms.model.BookingEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookingAdapter {

	public static BookingEntity toEntity(BookingDto bookingDto) {

		return BookingEntity.builder()
				.id(bookingDto.getId())
				.bookingtime(bookingDto.getBookingtime())
				.allottedSeats(bookingDto.getAllottedSeats())
				.amount(bookingDto.getAmount())
				.ticketDownloadlink(bookingDto.getTicketDownloadlink())
				.status(bookingDto.getStatus())
				.show(ShowAdapter.toEntity(bookingDto.getShow()))
				.user(UserAdapter.toEntity(bookingDto.getUser()))
				.build();

	}

	public static BookingDto toDto(BookingEntity bookingEntity) {

		return BookingDto.builder()
				.id(bookingEntity.getId())
				.bookingtime(bookingEntity.getBookingtime())
				.allottedSeats(bookingEntity.getAllottedSeats())
				.amount(bookingEntity.getAmount())
				.ticketDownloadlink(bookingEntity.getTicketDownloadlink())
				.status(bookingEntity.getStatus())
				.show(ShowAdapter.toDto(bookingEntity.getShow()))
				.user(UserAdapter.toDto(bookingEntity.getUser()))
				.build();
	}

}