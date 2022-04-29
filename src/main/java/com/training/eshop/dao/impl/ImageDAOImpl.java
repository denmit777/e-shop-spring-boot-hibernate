package com.training.eshop.dao.impl;

import com.training.eshop.dao.ImageDAO;
import com.training.eshop.model.Image;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ImageDAOImpl implements ImageDAO {

    private static final String QUERY_SELECT_FROM_IMAGE = "from Image";

    @PersistenceContext
    private EntityManager entityManager;

    public ImageDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Image image) {
        entityManager.persist(image);
    }

    @Override
    public Image getById(Long id) {
        return entityManager.find(Image.class, id);
    }

    @Override
    public List<Image> getAll() {
        return entityManager.createQuery(QUERY_SELECT_FROM_IMAGE).getResultList();
    }

    @Override
    public void deleteById(Long id) {
        final Image image = getById(id);

        entityManager.remove(image);
    }
}
