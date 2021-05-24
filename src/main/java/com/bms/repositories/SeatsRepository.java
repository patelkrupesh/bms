package com.bms.repositories;

import com.bms.model.SeatsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatsRepository extends CrudRepository<SeatsEntity, Long>{
}