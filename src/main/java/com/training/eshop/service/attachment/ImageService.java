package com.training.eshop.service.attachment;

import com.training.eshop.model.Image;

import java.util.List;

public interface ImageService {

    void save(Image image);

    Image getById(Long id);

    List<Image> getAll();

    void deleteById(Long id);
}
