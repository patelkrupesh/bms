package com.bms.repositories;

import com.bms.model.TheaterEntity;
import com.bms.model.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepository extends CrudRepository<TheaterEntity, Long> {

    @Query("SELECT u FROM TheaterEntity u WHERE u.name=:name")
    Iterable<TheaterEntity> findTheaterEntitiesByName(@Param("name") String u);

    @Query("SELECT u FROM UserEntity u WHERE u.username=:username")
    TheaterEntity findTheaterEntitiesByUsername(@Param("username") String u);

    @Query("SELECT u FROM UserEntity u WHERE u.username=:username AND u.password=:password")
    TheaterEntity findTheaterEntitiesByCredentials(@Param("username") String username, @Param("password") String password);

    @Query("SELECT u FROM TheaterEntity u WHERE u.city=:city")
    Iterable<TheaterEntity> findTheaterEntitiesByCity(@Param("city") String u);

}
