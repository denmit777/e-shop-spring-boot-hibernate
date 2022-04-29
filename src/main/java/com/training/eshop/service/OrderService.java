package com.training.eshop.service;

import com.training.eshop.model.Order;

import java.util.List;

public interface OrderService {

    Order create(Order order);

    Order getById(Long id);

    Order getByUserId(Long userId);

    List<Order> getAll();

    Order save(Order order);

    Order update(Long id, Order order);

    void deleteById(Long id);

    void deleteAll();
}
