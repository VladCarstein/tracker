package com.example.tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskCreateRequest(
        @NotBlank
        @Size(max = 100)
        String title,
        String description,
        Long projectId

) {

}
