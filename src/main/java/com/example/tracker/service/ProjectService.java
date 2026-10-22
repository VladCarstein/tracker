package com.example.tracker.service;

import com.example.tracker.dto.ProjectCreateRequest;
import com.example.tracker.dto.ProjectResponse;
import com.example.tracker.entity.Project;
import com.example.tracker.exception.ProjectNotFoundException;
import com.example.tracker.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<ProjectResponse> findAll() {
        return projectRepository.findAll().stream().map(ProjectResponse::from).toList();
    }

    public ProjectResponse create(ProjectCreateRequest request) {
        Project project = createProject(request);
        project = projectRepository.save(project);
        return ProjectResponse.from(project);
    }

    public  ProjectResponse findById(Long id) {
        return ProjectResponse.from(getByIdOrThrow(id));
    }

    private Project getByIdOrThrow(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
    }

    private Project createProject(ProjectCreateRequest request) {
        Project project = new Project();
        project.setName(request.name());
        project.setDescription(request.description());
        return project;
    }

}
