package com.training.eshop.dao.impl;

import com.training.eshop.model.User;
import com.training.eshop.dao.UserDAO;

import java.util.List;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final String QUERY_SELECT_FROM_USER = "from User";
    private static final String QUERY_SELECT_FROM_USER_BY_LOGIN = "from User u where u.login = :login";

    @PersistenceContext
    private EntityManager entityManager;

    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getByLogin(String login) {
        return (User) entityManager.createQuery(QUERY_SELECT_FROM_USER_BY_LOGIN)
                .setParameter("login", login).getSingleResult();
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public List<User> getAll() {
        return entityManager.createQuery(QUERY_SELECT_FROM_USER).getResultList();
    }

    @Override
    public void deleteById(Long id) {
        final User user = getById(id);

        entityManager.remove(user);
    }

    @Override
    public void deleteAll() {
        for (User user : getAll()) {
            entityManager.remove(user);
        }
    }
}
