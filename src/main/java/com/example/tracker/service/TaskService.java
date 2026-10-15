package com.example.tracker.service;

import com.example.tracker.dto.TaskCreateRequest;
import com.example.tracker.dto.TaskResponse;
import com.example.tracker.dto.TaskUpdateRequest;
import com.example.tracker.entity.Project;
import com.example.tracker.entity.Task;
import com.example.tracker.exception.ProjectNotFoundException;
import com.example.tracker.exception.TaskNotFoundException;
import com.example.tracker.repository.ProjectRepository;
import com.example.tracker.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public List<TaskResponse> findAll() {
        return taskRepository.findAll().stream().map(TaskResponse::from).toList();
    }

    public TaskResponse create(TaskCreateRequest request) {
        Task task = createTask(request);

        if (request.projectId() != null ) {
            Project project = projectRepository.findById(request.projectId())
                    .orElseThrow(()-> new ProjectNotFoundException(request.projectId()));
            task.setProject(project);
        }

        Task save = taskRepository.save(task);
        return TaskResponse.from(save);
    }

    public TaskResponse findById(Long id) {
        return TaskResponse.from(getByIdOrThrow(id));
    }

    public List<TaskResponse> findByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId).stream().map(TaskResponse::from).toList();
    }

    @Transactional
    public TaskResponse update(Long id, TaskUpdateRequest update) {
        Task task = getByIdOrThrow(id);
        updateTask(task, update);
        taskRepository.save(task);
        return TaskResponse.from(task);
    }

    public void deleteById(Long id) {
        Task task = getByIdOrThrow(id);
        taskRepository.delete(task);
    }

    private Task getByIdOrThrow(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    private Task createTask(TaskCreateRequest request) {
        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        return task;
    }

    private void updateTask(Task task, TaskUpdateRequest update) {
        task.setTitle(update.title());
        task.setDescription(update.description());
        task.setStatus(update.status());
    }
}
