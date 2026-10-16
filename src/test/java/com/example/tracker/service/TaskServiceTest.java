package com.example.tracker.service;

import com.example.tracker.dto.TaskCreateRequest;
import com.example.tracker.dto.TaskResponse;
import com.example.tracker.entity.Project;
import com.example.tracker.entity.Task;
import com.example.tracker.enumerations.TaskStatus;
import com.example.tracker.exception.ProjectNotFoundException;
import com.example.tracker.exception.TaskNotFoundException;
import com.example.tracker.repository.ProjectRepository;
import com.example.tracker.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void findById_whenTaskNotFound_throwsException() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.findById(999L));
    }

    @Test
    void findById_whenTaskFound_returnTask() {
        Long taskId = 1L;

        Task task = new Task();
        task.setId(taskId);
        task.setTitle("Task 1");
        task.setDescription("TaskDescription 1");
        task.setCreatedAt(OffsetDateTime.now());

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        TaskResponse response = taskService.findById(taskId);

        assertEquals(taskId, response.id());
        assertEquals("Task 1", response.title());
        assertEquals(TaskStatus.TODO, response.status());
    }

    @Test
    void deleteById_whenTaskNotFound_throwsException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteById(1L));
    }

    @Test
    void deleteById_whenTaskFound_returnTask() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task 1");
        task.setDescription("TaskDescription 1");
        task.setCreatedAt(OffsetDateTime.now());

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.deleteById(1L);

        verify(taskRepository).delete(task);
    }

    @Test
    void create() {
        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        TaskCreateRequest request = new TaskCreateRequest("F", "B", 999L);

        assertThrows(ProjectNotFoundException.class, () -> taskService.create(request));

        verify(taskRepository, never()).save(any());
    }

    @Test
    void createTask() {

        Project project = new Project();
        project.setId(1L);

        TaskCreateRequest request = new TaskCreateRequest("F", "B", 1L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task saved = invocation.getArgument(0);
            saved.setId(1L);
            saved.setCreatedAt(OffsetDateTime.now());
            return saved;
        });

        TaskResponse response = taskService.create(request);

        assertEquals("F", response.title());
        assertEquals("B", response.description());
        assertEquals(1L, response.projectId());
    }
}

