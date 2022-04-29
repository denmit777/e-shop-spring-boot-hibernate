package com.training.eshop.dao;

import com.training.eshop.model.Order;

import java.util.List;

public interface OrderDAO {

    void create(Order order);

    Order getById(Long id);

    Order getByUserId(Long userId);

    void save(Order order);

    List<Order> getAll();

    void deleteById(Long id);

    void deleteAll();
}
