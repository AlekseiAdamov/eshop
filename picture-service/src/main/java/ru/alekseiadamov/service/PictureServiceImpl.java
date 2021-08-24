package ru.alekseiadamov.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.alekseiadamov.db.dao.PictureRepository;
import ru.alekseiadamov.db.entity.Picture;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PictureServiceImpl implements PictureService {

    @Value("${picture.storage.path}")
    private String storagePath;

    private final PictureRepository repository;

    @Autowired
    public PictureServiceImpl(PictureRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<String> getPictureContentTypeById(Long id) {
        return repository.findById(id)
                .map(Picture::getContentType);
    }

    @Override
    public Optional<byte[]> getPictureDataById(Long id) {
        return repository.findById(id)
                .map(picture -> Path.of(storagePath, picture.getStorageUUID()))
                .filter(Files::exists)
                .map(path -> {
                    try {
                        return Files.readAllBytes(path);
                    } catch (IOException e) {
                        String message = String.format("Can't read file for picture with id %s: %s", id, e.getMessage());
                        log.error(message);
                        throw new UnableToReadFileException(message);
                    }
                });
    }

    @Override
    public String createPicture(byte[] pictureData) {
        String fileName = UUID.nameUUIDFromBytes(pictureData).toString();
        try (OutputStream outputStream = Files.newOutputStream(Path.of(storagePath, fileName))) {
            outputStream.write(pictureData);
        } catch (IOException e) {
            String message = String.format("Can't write file: %s", e.getMessage());
            log.error(message);
            throw new UnableToWriteFileException(message);
        }
        return fileName;
    }

    @Override
    public List<Picture> findAllByProductId(Long productId) {
        return repository.findAllByProductId(productId);
    }

    @Override
    public List<Picture> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Picture> findAllByProducts(List<Long> productIds) {
        return repository.findAll()
                .stream()
                .filter(picture -> productIds.contains(picture.getProduct().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Map<Long, Picture> getFirstPicturesOfProducts(List<Long> productIds) {
        return findAllByProducts(productIds)
                .stream()
                .collect(Collectors.toMap(
                        picture -> picture.getProduct().getId(),
                        picture -> picture.getProduct().getPictures().get(0),
                        (picture1, picture2) -> picture1
                ));
    }

    @Override
    public void deleteById(Long id) {
        final Optional<Picture> picture = repository.findById(id);
        if (picture.isEmpty()) {
            return;
        }
        try {
            Files.delete(Path.of(storagePath, picture.get().getStorageUUID()));
        } catch (IOException e) {
            log.error("Unable to delete file of the picture with id {}: {}", id, e.getMessage());
        } finally {
            repository.deleteById(id);
        }
    }
}
