package com.example.tracker.dto;

import com.example.tracker.entity.Task;
import com.example.tracker.enumerations.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime createdAt,
        Long projectId
) {

    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt().toLocalDateTime(),
                task.getProject() != null ? task.getProject().getId() : null
        );
    }
}
