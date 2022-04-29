package com.training.eshop.dao;

import com.training.eshop.model.Good;

import java.util.List;

public interface GoodDAO {

    void save(Good good);

    Good getById(Long id);

    void update(Good good);

    List<Good> getAll();

    void deleteById(Long id);

    void deleteAll();
}
