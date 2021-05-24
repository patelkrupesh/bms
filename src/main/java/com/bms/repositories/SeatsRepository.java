package com.bms.repositories;

import com.bms.model.MovieEntity;
import com.bms.model.SeatsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatsRepository extends CrudRepository<SeatsEntity, Long>{
    @Query("SELECT m FROM SeatsEntity m WHERE m.show=:show")
    Iterable<SeatsEntity> findSeatsByShow(@Param("show") Long show);

    @Query("SELECT m FROM SeatsEntity m WHERE m.booking.id=:bookingid")
    Iterable<SeatsEntity> findSeatsByBooking(@Param("bookingid") Long bookingid);

    @Override
    Iterable<SeatsEntity> findAllById(Iterable<Long> longs);
}