package com.training.eshop.service;

import com.training.eshop.model.User;

import java.util.List;

public interface UserService {

    User save(User user);

    User getById(Long id);

    User update(Long id, User user);

    List<User> getAll();

    void deleteById(Long id);

    void deleteAll();
}
