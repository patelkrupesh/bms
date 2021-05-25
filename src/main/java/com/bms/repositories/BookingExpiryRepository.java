package com.bms.repositories;

import com.bms.model.BookingExpiryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingExpiryRepository extends CrudRepository<BookingExpiryEntity, Long>{
    @Query("SELECT m FROM BookingExpiryEntity m WHERE m.expirytime> CURRENT_TIMESTAMP ")
    Iterable<BookingExpiryEntity> findExpiredBookings();

    @Query("SELECT m FROM BookingExpiryEntity m WHERE m.booking.id=:bookingid")
    Iterable<BookingExpiryEntity> findBookingExpiryEntitiesByBooking(@Param("bookingid") Long bookingid);

    @Override
    void deleteAll(Iterable<? extends BookingExpiryEntity> entities);
}