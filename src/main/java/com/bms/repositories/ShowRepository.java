package com.bms.repositories;

import com.bms.model.ShowEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends CrudRepository<ShowEntity, Integer>{
}