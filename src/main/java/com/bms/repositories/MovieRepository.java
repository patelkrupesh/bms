package com.bms.repositories;

import com.bms.model.MovieEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<MovieEntity, Long>{

	@Query("SELECT m FROM MovieEntity m WHERE m.name=:name")
	Iterable<MovieEntity> findMovieByName(@Param("name") String name);
}