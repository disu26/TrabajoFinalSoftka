package com.bingo.dao;

import com.bingo.domain.Game;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GameDao extends CrudRepository<Game, Long> {

    @Modifying
    @Query("update Game game set game.inProgress= :inProgress where game.id = :id")
    public void updateInProgress(
            @Param(value = "id") Long id,
            @Param(value = "inProgress") boolean inProgress
    );

    @Modifying
    @Query("update Game game set game.started= :started where game.id = :id")
    public void updateStarted(
            @Param(value = "id") Long id,
            @Param(value = "started") boolean started
    );

    @Modifying
    @Query("update Game game set game.finished= :finished where game.id = :id")
    public void updateFinished(
            @Param(value = "id") Long id,
            @Param(value = "finished") boolean finished
    );
}
