package com.bingo.dao;

import com.bingo.domain.Card;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CardDao extends CrudRepository<Card, Long> {

    @Modifying
    @Query("update Card card set card.winner = :winner where card.id = :id")
    public void updateWinner(
            @Param(value = "id") Long id,
            @Param(value = "winner") boolean winner
    );


}
