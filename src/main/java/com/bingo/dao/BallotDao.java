package com.bingo.dao;

import com.bingo.domain.Ballot;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BallotDao extends CrudRepository<Ballot, Long> {

    @Modifying
    @Query("update Ballot bal set bal.out = :out")
    public void restartBallot(
            @Param(value = "out") boolean out
    );

    @Modifying
    @Query("update Ballot bal set bal.letter = :letter  where bal.id = :id")
    public void updateLetter(
            @Param(value = "id") Long id,
            @Param(value = "letter") String letter
    );

    @Modifying
    @Query("update Ballot bal set bal.number = :number  where bal.id = :id")
    public void updateNumber(
            @Param(value = "id") Long id,
            @Param(value = "number") String number
    );

    @Modifying
    @Query("update Ballot bal set bal.out = :out  where bal.id = :id")
    public void updateOut(
            @Param(value = "id") Long id,
            @Param(value = "out") boolean out
    );

    @Query("select bal from Ballot bal where bal.out = :out")
    public Iterable<Ballot> findBallotOut(
            @Param(value = "out") boolean out
    );
}
