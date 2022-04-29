package com.training.eshop.service;

import com.training.eshop.model.Good;

import java.util.List;

public interface GoodService {

    Good save(Good good);

    Good getById(Long id);

    Good update(Long id, Good good);

    List<Good> getAll();

    void deleteById(Long id);

    void deleteAll();
}
