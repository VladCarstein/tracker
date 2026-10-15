package com.example.tracker.controller;

import com.example.tracker.dto.ProjectCreateRequest;
import com.example.tracker.dto.ProjectResponse;
import com.example.tracker.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public List<ProjectResponse> findAll() {
        return projectService.findAll();
    }

    @PostMapping
    public ProjectResponse create(@Valid @RequestBody ProjectCreateRequest projectCreateRequest) {
        return projectService.create(projectCreateRequest);
    }

}
