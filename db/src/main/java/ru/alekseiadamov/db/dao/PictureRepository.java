package ru.alekseiadamov.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alekseiadamov.db.entity.Picture;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long> {

    List<Picture> findAllByProductId(Long productId);
}
