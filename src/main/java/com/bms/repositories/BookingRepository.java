package com.bms.repositories;

import com.bms.model.BookingEntity;
import com.bms.model.ScreenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends CrudRepository<BookingEntity, Integer>{
}