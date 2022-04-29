package com.training.eshop.dao;

import com.training.eshop.model.User;

import java.util.List;

public interface UserDAO {

    void save(User user);

    User getById(Long id);

    User getByLogin(String login);

    void update(User user);

    List<User> getAll();

    void deleteById(Long id);

    void deleteAll();
}

