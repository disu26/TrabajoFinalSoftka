package com.bingo.dao;

import com.bingo.domain.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameDao extends CrudRepository<Game, Long> {
}
