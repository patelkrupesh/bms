package com.bms.repositories;

import com.bms.model.ScreenEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenRepository extends CrudRepository<ScreenEntity, Long>{
}