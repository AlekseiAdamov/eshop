package ru.alekseiadamov.service;

public class UnableToWriteFileException extends RuntimeException {
    public UnableToWriteFileException(String message) {
        super(message);
    }
}
