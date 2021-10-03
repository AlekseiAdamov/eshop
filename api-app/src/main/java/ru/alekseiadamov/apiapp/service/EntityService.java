package ru.alekseiadamov.apiapp.service;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface EntityService<T, P> {

    List<T> findAll();

    Optional<T> findById(Long id);

    Page<T> findWithFilter(P params);

    T getById(Long id);

    Optional<T> findByName(String name);
}
