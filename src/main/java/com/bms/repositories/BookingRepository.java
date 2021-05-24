package com.bms.repositories;

import com.bms.model.BookingEntity;
import com.bms.model.ScreenEntity;
import com.bms.model.SeatsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends CrudRepository<BookingEntity, Long>{
    @Query("SELECT m FROM BookingEntity m WHERE m.user.username=:username")
    Iterable<BookingEntity> findBookingEntitiesByUser(@Param("username") String username);
}