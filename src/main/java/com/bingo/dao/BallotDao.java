package com.bingo.dao;

import com.bingo.domain.Ballot;
import org.springframework.data.repository.CrudRepository;

public interface BallotDao extends CrudRepository<Ballot, Long> {

}
