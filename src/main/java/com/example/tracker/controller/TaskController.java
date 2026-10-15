package com.example.tracker.controller;

import com.example.tracker.dto.TaskCreateRequest;
import com.example.tracker.dto.TaskResponse;
import com.example.tracker.dto.TaskUpdateRequest;
import com.example.tracker.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<TaskResponse> getTasks() {
        return taskService.findAll();
    }

    @PostMapping
    public TaskResponse create(@Valid @RequestBody TaskCreateRequest request) {
        return taskService.create(request);
    }

    @GetMapping("/{id}")
    public TaskResponse findById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @GetMapping(params = "projectId")
    public List<TaskResponse> findByProjectId(Long projectId) {
        return taskService.findByProjectId(projectId);
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id, @Valid @RequestBody TaskUpdateRequest updated) {
        return taskService.update(id, updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        taskService.deleteById(id);
    }
}
