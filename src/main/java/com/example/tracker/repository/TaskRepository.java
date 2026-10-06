package com.example.tracker.repository;

import com.example.tracker.entity.Task;
import com.example.tracker.enumerations.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);
}
