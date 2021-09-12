package ru.alekseiadamov.adminapp.service;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface EntityRetrieverService<T, P> {

    List<T> findAll();

    Optional<T> findById(Long id);

    Page<T> findWithFilter(P params);

    T getById(Long id);

    Optional<T> findByName(String name);
}
