package ru.alekseiadamov.adminapp.service;

public interface EntityManipulatorService<T> {

    void deleteById(Long id);

    void save(T entity);

}
