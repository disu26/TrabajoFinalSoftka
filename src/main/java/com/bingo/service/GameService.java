package com.bingo.service;

import com.bingo.dao.GameDao;
import com.bingo.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GameService implements IGameService{

    @Autowired
    private GameDao gameDao;

    @Override
    @Transactional(readOnly = true)
    public List<Game> list() {
        return (List<Game>) gameDao.findAll();
    }

    @Override
    @Transactional
    public Game save(Game game) {
        return gameDao.save(game);
    }

    @Override
    @Transactional
    public Game update(Long id, Game game) {
        game.setId(id);
        return gameDao.save(game);
    }

    @Transactional
    public void updateInProgress(Long id, Game game, boolean status) {
        game.setId(id);
        gameDao.updateInProgress(game.getId(), status);
    }

    @Transactional
    public void updateStarted(Long id, Game game, boolean status) {
        game.setId(id);
        gameDao.updateStarted(game.getId(), status);
    }

    @Transactional
    public void updateFinished(Long id, Game game, boolean status) {
        game.setId(id);
        gameDao.updateFinished(game.getId(), status);
    }

    @Transactional
    public void updateStartTime(Long id, Game game, Date startTime) {
        game.setId(id);
        gameDao.updateStartTime(game.getId(), startTime);
    }

    @Override
    @Transactional
    public void delete(Game game) {
        gameDao.delete(game);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Game> findGame(Game game) {
        return gameDao.findById(game.getId());
    }

    @Transactional(readOnly = true)
    public Optional<Game> findGameInProgress() {
        return gameDao.findGamesInProgress(true);
    }

    @Transactional(readOnly = true)
    public Optional<Game> findAnyGame() {
        return gameDao.findAnyGame(Long.valueOf(1));
    }

    @Transactional(readOnly = true)
    public Optional<Game> findGameStarted() {
        return gameDao.findGameStarted(true);
    }
}
