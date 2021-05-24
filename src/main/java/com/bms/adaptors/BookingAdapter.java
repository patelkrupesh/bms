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
				.showEntity(ShowAdapter.toEntity(bookingDto.getShowDto()))
				.userEntity(UserAdapter.toEntity(bookingDto.getUserDto()))
				.build();

	}

	public static BookingDto toDto(BookingEntity bookingEntity) {

		return BookingDto.builder()
				.id(bookingEntity.getId())
				.bookingtime(bookingEntity.getBookingtime())
				.allottedSeats(bookingEntity.getAllottedSeats())
				.amount(bookingEntity.getAmount())
				.ticketDownloadlink(bookingEntity.getTicketDownloadlink())
				.showDto(ShowAdapter.toDto(bookingEntity.getShowEntity()))
				.userDto(UserAdapter.toDto(bookingEntity.getUserEntity()))
				.build();
	}

}