package com.bingo.service;

import com.bingo.domain.Game;

import java.util.List;
import java.util.Optional;

public interface IGameService {

    public List<Game> list();

    public Game save(Game game);

    public Game update(Long id, Game game);

    public void delete(Game game);

    public Optional<Game> findGame(Game game);
}
