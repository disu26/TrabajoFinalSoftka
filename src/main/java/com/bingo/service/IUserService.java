package com.bingo.service;

import com.bingo.domain.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    public List<User> list();

    public User save(User user);

    public User update(Long id, User user);

    public void delete(User user);

}
