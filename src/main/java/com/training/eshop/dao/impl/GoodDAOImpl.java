package com.training.eshop.dao.impl;

import com.training.eshop.dao.GoodDAO;
import com.training.eshop.model.Good;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GoodDAOImpl implements GoodDAO {

    private static final String QUERY_SELECT_FROM_GOOD = "from Good";

    @PersistenceContext
    private EntityManager entityManager;

    public GoodDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Good good) {
        entityManager.persist(good);
    }

    @Override
    public Good getById(Long id) {
        return entityManager.find(Good.class, id);
    }

    @Override
    public void update(Good good) {
        entityManager.merge(good);
    }

    @Override
    public List<Good> getAll() {
        return entityManager.createQuery(QUERY_SELECT_FROM_GOOD).getResultList();
    }

    @Override
    public void deleteById(Long id) {
        final Good good = getById(id);

        entityManager.remove(good);
    }

    @Override
    public void deleteAll() {
        for (Good good : getAll()) {
            entityManager.remove(good);
        }
    }
}
