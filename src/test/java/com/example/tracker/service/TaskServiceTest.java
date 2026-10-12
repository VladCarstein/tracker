package com.example.tracker.service;

import com.example.tracker.dto.TaskResponse;
import com.example.tracker.entity.Task;
import com.example.tracker.enumerations.TaskStatus;
import com.example.tracker.exception.TaskNotFoundException;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void findById_whenTaskNotFound_throwsException() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.findById(999L));
    }

    @Test
    void findById_whenTaskFound_returnTask() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task 1");
        task.setDescription("TaskDescription 1");
        task.setCreatedAt(OffsetDateTime.now());

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        TaskResponse response = taskService.findById(1L);

        assertEquals(1L, response.id());
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
}

