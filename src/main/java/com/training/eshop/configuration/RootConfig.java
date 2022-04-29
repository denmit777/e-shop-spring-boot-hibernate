package com.training.eshop.configuration;

import com.training.eshop.dao.impl.OrderDAOImpl;
import com.training.eshop.service.GoodService;
import com.training.eshop.service.impl.CartServiceImpl;
import com.training.eshop.service.impl.GoodServiceImpl;
import com.training.eshop.service.impl.OrderServiceImpl;
import com.training.eshop.dao.GoodDAO;
import com.training.eshop.dao.ImageDAO;
import com.training.eshop.dao.OrderDAO;
import com.training.eshop.dao.UserDAO;
import com.training.eshop.dao.impl.GoodDAOImpl;
import com.training.eshop.dao.impl.ImageDAOImpl;
import com.training.eshop.dao.impl.UserDAOImpl;
import com.training.eshop.service.CartService;
import com.training.eshop.service.OrderService;
import com.training.eshop.service.UserService;
import com.training.eshop.service.attachment.ImageService;
import com.training.eshop.service.attachment.impl.ImageServiceImpl;
import com.training.eshop.service.impl.UserServiceImpl;

import com.training.eshop.service.impl.security.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.EntityManager;

@Configuration
@ComponentScan({"com.training.eshop.service", "com.training.eshop.dao"})
public class RootConfig {

    @Autowired
    private EntityManager entityManager;

    @Bean
    public GoodDAO goodDAO() {
        return new GoodDAOImpl(entityManager);
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAOImpl(entityManager);
    }

    @Bean
    public OrderDAO orderDAO() {
        return new OrderDAOImpl(entityManager);
    }

    @Bean
    public ImageDAO imageDAO() {
        return new ImageDAOImpl(entityManager);
    }

    @Bean
    public GoodService goodService() {
        return new GoodServiceImpl(goodDAO());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(orderDAO());
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userDAO());
    }

    @Bean
    public ImageService imageService() {
        return new ImageServiceImpl(imageDAO());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userDAO());
    }

    @Bean
    public CartService cartService() {
        return new CartServiceImpl();
    }
}
