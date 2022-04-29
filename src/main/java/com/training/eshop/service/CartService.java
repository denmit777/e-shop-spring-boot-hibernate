package com.training.eshop.service;

import com.training.eshop.model.Cart;

import java.math.BigDecimal;

public interface CartService {

    String print(Cart cart);

    BigDecimal getTotalPrice(Cart cart);
}
