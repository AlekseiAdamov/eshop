package ru.alekseiadamov.pictureservice.service;

public class UnableToReadFileException extends RuntimeException {
    public UnableToReadFileException(String message) {
        super(message);
    }
}
