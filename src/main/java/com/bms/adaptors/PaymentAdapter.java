package com.bms.adaptors;

import com.bms.dto.PaymentDto;
import com.bms.model.PaymentEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentAdapter {

	public static PaymentEntity toEntity(PaymentDto paymentDto) {

		return PaymentEntity.builder()
				.id(paymentDto.getId())
				.name(paymentDto.getName())
				.status(paymentDto.getStatus())
				.gateway(paymentDto.getGateway())
				.bookingEntity(BookingAdapter.toEntity(paymentDto.getBookingDto()))
				.build();

	}

	public static PaymentDto toDto(PaymentEntity paymentEntity) {

		return PaymentDto.builder()
				.id(paymentEntity.getId())
				.name(paymentEntity.getName())
				.status(paymentEntity.getStatus())
				.gateway(paymentEntity.getGateway())
				.bookingDto(BookingAdapter.toDto(paymentEntity.getBookingEntity()))
				.build();
	}

}