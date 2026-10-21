package com.example.tracker.service;

import com.example.tracker.dto.ProjectCreateRequest;
import com.example.tracker.dto.ProjectResponse;
import com.example.tracker.entity.Project;
import com.example.tracker.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    ProjectRepository projectRepository;

    @InjectMocks
    ProjectService projectService;

    @Test
    void create_savesProjectAndReturnsResponse() {
        ProjectCreateRequest request = new ProjectCreateRequest("F", "B");

        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> {
            Project saved = invocation.getArgument(0);
            saved.setId(1L);
            saved.setName("F");
            saved.setDescription("B");
            saved.setCreatedAt(OffsetDateTime.now());
            return saved;
        });

        ProjectResponse response = projectService.create(request);

        assertEquals("F", response.name());
        assertEquals("B", response.description());
        assertEquals(1L, response.id());

    }
}
