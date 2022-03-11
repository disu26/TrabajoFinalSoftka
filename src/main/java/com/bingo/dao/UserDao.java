package com.bingo.dao;

import com.bingo.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDao extends CrudRepository<User, String> {

    @Modifying
    @Query("update User user set user.winner= :winner where user.id = :id")
    public void updateWinner(
            @Param(value = "id") Long id,
            @Param(value = "winner") boolean winner
    );

    @Query("select user from User user where user.mongoId = :mongoId")
    public Optional<User> findByMongoId(
            @Param(value = "mongoId") String mongoId
    );
}
