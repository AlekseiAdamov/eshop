package ru.alekseiadamov.service;

import ru.alekseiadamov.db.entity.Picture;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PictureService {

    Optional<String> getPictureContentTypeById(Long id);

    Optional<byte[]> getPictureDataById(Long id);

    String createPicture(byte[] pictureData);

    List<Picture> findAllByProductId(Long productId);

    List<Picture> findAll();

    List<Picture> findAllByProducts(List<Long> productIds);

    Map<Long, Picture> getFirstPicturesOfProducts(List<Long> productIds);

    void deleteById(Long id);
}
