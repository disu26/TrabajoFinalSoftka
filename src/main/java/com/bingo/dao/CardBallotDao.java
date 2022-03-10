package com.bingo.dao;

import com.bingo.domain.CardBallot;
import org.springframework.data.repository.CrudRepository;

public interface CardBallotDao extends CrudRepository<CardBallot, Long> {
}
