package com.training.eshop.dao.impl;

import com.training.eshop.model.Order;
import com.training.eshop.dao.OrderDAO;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private static final String QUERY_SELECT_FROM_ORDER = "from Order";
    private static final String QUERY_SELECT_FROM_ORDER_BY_USER_ID = "from Order o where o.userId = :userId";

    @PersistenceContext
    private EntityManager entityManager;

    public OrderDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Order order) {
        entityManager.persist(order);
    }

    @Override
    public Order getById(Long id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public Order getByUserId(Long userId) {
        return (Order) entityManager.createQuery(QUERY_SELECT_FROM_ORDER_BY_USER_ID)
                .setParameter("userId", userId).getSingleResult();
    }

    @Override
    public void save(Order order) {
        entityManager.merge(order);
    }

    @Override
    public List<Order> getAll() {
        return entityManager.createQuery(QUERY_SELECT_FROM_ORDER).getResultList();
    }

    @Override
    public void deleteById(Long id) {
        final Order order = getById(id);

        entityManager.remove(order);
    }

    @Override
    public void deleteAll() {
        for (Order order : getAll()) {
            entityManager.remove(order);
        }
    }
}
