package com.example.tracker.dto;

import com.example.tracker.enumerations.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskUpdateRequest (
        @NotBlank
        @Size(max = 100)
        String title,

        String description,

        @NotNull
        TaskStatus status) {
}
