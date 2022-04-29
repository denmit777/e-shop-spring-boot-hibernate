package com.training.eshop.dao;

import com.training.eshop.model.Image;

import java.util.List;

public interface ImageDAO {

    void save(Image image);

    Image getById(Long id);

    List<Image> getAll();

    void deleteById(Long id);
}
