package com.example.tracker.dto;

import com.example.tracker.enumerations.TaskStatus;

public record TaskUpdateRequest (String title, String description, TaskStatus status) {
}
