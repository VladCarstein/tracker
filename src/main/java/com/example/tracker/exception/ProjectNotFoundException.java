package com.example.tracker.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(Long id) {
        super("Проект с id " + id + " не найден");
    }
}
