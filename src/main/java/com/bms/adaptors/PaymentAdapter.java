package com.bms.adaptors;

import com.bms.dto.PaymentDto;
import com.bms.model.PaymentEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentAdapter {

	public static PaymentEntity toEntity(PaymentDto paymentDto) {

		return PaymentEntity.builder()
				.id(paymentDto.getId())
				.amount(paymentDto.getAmount())
				.status(paymentDto.getStatus())
				.gateway(paymentDto.getGateway())
				.booking(BookingAdapter.toEntity(paymentDto.getBooking()))
				.build();

	}

	public static PaymentDto toDto(PaymentEntity paymentEntity) {

		return PaymentDto.builder()
				.id(paymentEntity.getId())
				.amount(paymentEntity.getAmount())
				.status(paymentEntity.getStatus())
				.gateway(paymentEntity.getGateway())
				.booking(BookingAdapter.toDto(paymentEntity.getBooking()))
				.build();
	}

}