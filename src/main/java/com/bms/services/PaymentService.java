package com.bms.services;

import com.bms.adaptors.PaymentAdapter;
import com.bms.dto.PaymentDto;
import com.bms.enums.PaymentStatus;
import com.bms.model.PaymentEntity;
import com.bms.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentDto createPayment(PaymentDto paymentDto) throws Exception {
        paymentDto.setStatus(PaymentStatus.NOT_STARTED);
        PaymentEntity dbData = paymentRepository.save(PaymentAdapter.toEntity(paymentDto));
        return PaymentAdapter.toDto(dbData);
    }

    public PaymentDto updatePayment(PaymentDto paymentDto) throws Exception {
        Optional<PaymentEntity> optionalDbData = paymentRepository.findById(paymentDto.getId());
        if (!optionalDbData.isPresent()) {
            throw new Exception("Unable to find payment id : " + paymentDto.getId());
        }
        PaymentEntity dbData = paymentRepository.save(PaymentAdapter.toEntity(paymentDto));
        if (dbData == null) {
            throw new Exception("Unable to update payment id : " + paymentDto.getId());
        }
        return PaymentAdapter.toDto(dbData);
    }

    public PaymentEntity getPaymentEntityFromBookingId(Long bookingId){
        return paymentRepository.findByBookingId(bookingId);
    }
}