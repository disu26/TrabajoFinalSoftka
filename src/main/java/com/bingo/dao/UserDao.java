package com.bingo.dao;

import com.bingo.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface UserDao extends CrudRepository<User, String> {

    @Modifying
    @Query("update User user set user.winner= :winner where user.id = :id")
    public void updateWinner(
            @Param(value = "id") Long id,
            @Param(value = "winner") boolean winner
    );

    @Modifying
    @Query("update User user set user.mongoId= :mongoId where user.id = :id")
    public void updateMongoId(
            @Param(value = "id") Long id,
            @Param(value = "mongoId") String mongoId
    );

    @Modifying
    @Query("update User user set user.cardId= :cardId where user.id = :id")
    public void updateCardId(
            @Param(value = "id") Long id,
            @Param(value = "cardId") Long cardId
    );

    @Modifying
    @Query("update User user set user.gameId= :gameId where user.id = :id")
    public void updateGameId(
            @Param(value = "id") Long id,
            @Param(value = "gameId") Long gameId
    );

    @Query("select user from User user where user.mongoId = :mongoId")
    public Optional<User> findByMongoId(
            @Param(value = "mongoId") String mongoId
    );

    @Query("select user from User user where user.gameId = :gameId")
    public Collection<User> findByGameId(
            @Param(value = "gameId") Long gameId
    );

}
