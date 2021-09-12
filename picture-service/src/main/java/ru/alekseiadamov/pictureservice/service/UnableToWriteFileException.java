package ru.alekseiadamov.pictureservice.service;

public class UnableToWriteFileException extends RuntimeException {
    public UnableToWriteFileException(String message) {
        super(message);
    }
}
