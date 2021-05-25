package com.bms.repositories;

import com.bms.model.PaymentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentEntity, Long>{
    @Query("SELECT m FROM PaymentEntity m WHERE m.booking.id=:bookingid")
    PaymentEntity findByBookingId(@Param("bookingid") Long bookingid);

}