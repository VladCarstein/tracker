package com.example.tracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProjectCreateRequest (
        @NotNull
        @Size(max = 100)
        String name,
        String description) {
}
