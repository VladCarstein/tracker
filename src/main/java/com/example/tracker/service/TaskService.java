package com.example.tracker.service;

import com.example.tracker.entity.Task;
import com.example.tracker.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Task update(Long id, Task update) {
        Task task = getById(id);
        task.setTitle(update.getTitle());
        task.setDescription(update.getDescription());
        task.setStatus(update.getStatus());
        return task;
    }

    public void deleteById(Long id) {
        Task task = getById(id);
        taskRepository.delete(task);
    }
}
