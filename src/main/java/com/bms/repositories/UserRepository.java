package com.bms.repositories;

import com.bms.model.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {

    @Query("SELECT u FROM UserEntity u WHERE u.username=:username")
    UserEntity findUserByUsername(@Param("username") String u);

    @Query("SELECT u FROM UserEntity u WHERE u.username=:username AND u.password=:password")
    UserEntity findUserByCredentials(@Param("username") String username, @Param("password") String password);

}
