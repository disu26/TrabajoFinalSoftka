package com.bingo.dao;

import com.bingo.domain.Ballot;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BallotDao extends CrudRepository<Ballot, Long> {
}
