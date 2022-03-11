package com.bingo.dao;

import com.bingo.domain.CardBallot;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface CardBallotDao extends CrudRepository<CardBallot, Long> {

    @Query("select cardBallot from CardBallot cardBallot where cardBallot.cardId = :cardId")
    public Collection<CardBallot> findByCardId(
            @Param(value = "cardId") Long cardId
    );
}
