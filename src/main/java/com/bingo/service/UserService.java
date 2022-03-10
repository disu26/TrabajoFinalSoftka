package com.bingo.service;

import com.bingo.dao.UserDao;
import com.bingo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class UserService implements IUserService{

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(readOnly = true)
    public List<User> list() {
        return (List<User>) userDao.findAll();
    }

    @Override
    @Transactional
    public User save(String id, User user) {
        user.setId(id);
        return userDao.save(user);
    }

    @Override
    @Transactional
    public User update(String id, User user) {
        user.setId(id);
        return userDao.save(user);
    }

    @Transactional
    public void updateGameId(String id, User user) {
        userDao.updateGameId(id, user.getGameId());
    }

    @Transactional
    public void updateCardId(String id, User user) {
        userDao.updateCardId(id, user.getCardId());
    }

    @Transactional
    public void updateWinner(String id, User user) {
        userDao.updateWinner(id, user.isWinner());
    }

    @Override
    @Transactional
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findContact(User user) {
        return userDao.findById(user.getId());
    }
}
