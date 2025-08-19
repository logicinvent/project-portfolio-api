package br.com.project.portfolio.rest.api.domain.service.impl;

import br.com.project.portfolio.rest.api.domain.model.BudgetByStatus;
import br.com.project.portfolio.rest.api.domain.model.Project;
import br.com.project.portfolio.rest.api.domain.model.ProjectStatus;
import br.com.project.portfolio.rest.api.domain.repository.ProjectRepository;
import br.com.project.portfolio.rest.api.domain.service.ProjectService;
import br.com.project.portfolio.rest.api.shared.exception.NotPermittedException;
import br.com.project.portfolio.rest.api.shared.exception.ProjectNotFoundException;
import br.com.project.portfolio.rest.api.shared.util.MembersUtil;
import br.com.project.portfolio.rest.api.shared.util.ProjectUtils;
import br.com.project.portfolio.rest.api.web.dto.request.ProjectRequest;
import br.com.project.portfolio.rest.api.web.dto.response.ProjectResponse;
import br.com.project.portfolio.rest.api.web.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static br.com.project.portfolio.rest.api.shared.util.Constants.PROJECT_NOT_FOUND;
import static br.com.project.portfolio.rest.api.shared.util.Constants.PROJECT_STATUS_NOT_PERMITTED;

@RequiredArgsConstructor
@Log
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;
    private final ProjectUtils projectUtils;
    private final MembersUtil membersUtil;

    @Override
    public ProjectResponse save(ProjectRequest request) {

        log.info("Starting process to save project data");

        log.info("Adding project manager data");
        var project = projectMapper.toEntity(request);
        project.setManager(membersUtil.getMemberFromProject(request.managerId()));
        project.setStatus(ProjectStatus.EM_ANALISE);

        log.info("Saving project data");
        project = projectRepository.save(project);
        ProjectResponse result = projectMapper.toResponse(project);

        log.info("Finishing process to save project data");

        return result;
    }

    @Transactional
    @Override
    public ProjectResponse update(UUID id, ProjectRequest request) {
        log.info("Starting process to update project data");

        log.info("Retrieving project information");
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(PROJECT_NOT_FOUND));

        log.info("Adding project manager data");
        project.setManager(membersUtil.getMemberFromProject(request.managerId()));

        log.info("Checking if the project status can be updated");
        if(!ProjectStatus.canTransition(project.getStatus(), request.status()))
            throw new NotPermittedException(PROJECT_STATUS_NOT_PERMITTED);

        project.setStatus(request.status());

        projectMapper.updateEntityFromRequest(request, project);
        ProjectResponse result = projectMapper.toResponse(projectRepository.save(project));

        log.info("Finishing process to update project data");

        return result;
    }

    @Override
    public void delete(UUID id) {
        log.info("Starting process to delete project");

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(PROJECT_NOT_FOUND));

        log.info("Checking if project can be deleted");
        if (projectUtils.nonDeletableStatuses().contains(project.getStatus()))
            throw new NotPermittedException("Deletion not allowed for project in status: " + project.getStatus());

        log.info("Deleting project from repository");
        projectRepository.deleteById(id);

        log.info("Project successfully deleted");
    }

    @Override
    public ProjectResponse findById(UUID id) {
        log.info("Retrieving project by ID");
        return projectRepository.findById(id)
                .map(projectMapper::toResponse)
                .orElseThrow(() -> new ProjectNotFoundException(PROJECT_NOT_FOUND));
    }

    @Override
    public Page<ProjectResponse> getList(String name, Pageable pageable) {
        log.info("Listing projects with optional name filter");
        if (Objects.isNull(name) || name.isBlank())
            return projectRepository.findAll(pageable).map(projectMapper::toResponse);
        return projectRepository.findByNameContainingIgnoreCase(name, pageable).map(projectMapper::toResponse);
    }

    @Override
    public Double averageClosedProjectsDurationDays() {
        log.info("Calculating average duration in days of closed projects");
        return projectRepository.averageClosedProjectsDurationDays();
    }

    @Override
    public List<BudgetByStatus> findTotalBudgetByStatus() {
        log.info("Finding total budget grouped by project status");
        return projectRepository.findTotalBudgetByStatus();
    }

    @Override
    public Long countByStatus(ProjectStatus status) {
        log.info("Counting projects by status");
        return projectRepository.countByStatus(status);
    }
}
