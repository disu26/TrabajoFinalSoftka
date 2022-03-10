package com.bingo.service;

import com.bingo.domain.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    public List<User> list();

    public User save(String id, User user);

    public User update(String id, User user);

    public void delete(User user);

    public Optional<User> findContact(User user);
}
