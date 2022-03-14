package com.bingo.dao;

import com.bingo.domain.CardBallot;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface CardBallotDao extends CrudRepository<CardBallot, Long> {

    @Modifying
    @Query("update CardBallot cb set cb.marked = :marked  where cb.cardBallotId = :id")
    public void updateMarked(
            @Param(value = "id") Long id,
            @Param(value = "marked") boolean marked
    );

    @Query("select cardBallot from CardBallot cardBallot where cardBallot.cardId = :cardId")
    public Collection<CardBallot> findByCardId(
            @Param(value = "cardId") Long cardId
    );

    @Query("select cardBallot from CardBallot cardBallot where cardBallot.cardId = :cardId " +
            "AND cardBallot.balId = :balId")
    public Optional<CardBallot> findByCardAndBallotId(
            @Param(value = "cardId") Long cardId,
            @Param(value = "balId") Long balId
    );
}
