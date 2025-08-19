package br.com.project.portfolio.rest.api.domain.service;

import br.com.project.portfolio.rest.api.domain.model.BudgetByStatus;
import br.com.project.portfolio.rest.api.domain.model.ProjectStatus;
import br.com.project.portfolio.rest.api.web.dto.request.ProjectRequest;
import br.com.project.portfolio.rest.api.web.dto.response.ProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Service layer contract for managing Projects.
 * <p>
 * Responsibilities include:
 * <ul>
 *   <li>Create, update, delete and retrieve projects</li>
 *   <li>Paginated listing with optional name filtering</li>
 *   <li>Business metrics and aggregations (budget by status, average duration)</li>
 * </ul>
 * <p>
 * Implementations are expected to enforce domain rules such as:
 * <ul>
 *   <li>Manager validation (occupation/contract) when saving</li>
 *   <li>Deletion constraints for specific statuses</li>
 *   <li>Status transition consistency when updating</li>
 * </ul>
 */
public interface ProjectService {

    /**
     * Creates a new project.
     *
     * @param request DTO with the project data to be persisted
     * @return the created project as a response DTO
     * @throws IllegalArgumentException if required fields are missing or invalid
     * @throws IllegalStateException    if domain rules are violated (e.g., manager not eligible)
     */
    ProjectResponse save(ProjectRequest request);

    /**
     * Updates an existing project identified by {@code id}.
     *
     * @param id      the project ID
     * @param request DTO with fields to update
     * @return the updated project as a response DTO
     * @throws java.util.NoSuchElementException if the project is not found
     * @throws IllegalStateException            if status transition or other domain rules are violated
     */
    ProjectResponse update(UUID id, ProjectRequest request);

    /**
     * Deletes a project by ID.
     * <p>
     * Implementations should prevent deletion when the project is in restricted statuses
     * (e.g., {@code INICIADO}, {@code EM_ANDAMENTO}, {@code ENCERRADO}).
     *
     * @param id the project ID
     * @throws java.util.NoSuchElementException if the project is not found
     * @throws IllegalStateException            if deletion is not permitted for the current status
     */
    void delete(UUID id);

    /**
     * Retrieves a project by ID.
     *
     * @param id the project ID
     * @return the project as a response DTO
     * @throws java.util.NoSuchElementException if the project is not found
     */
    ProjectResponse findById(UUID id);

    /**
     * Returns a paginated list of projects, optionally filtered by name (case-insensitive, implementation-defined).
     *
     * @param name     optional name filter; when {@code null}, returns all
     * @param pageable pagination and sorting information
     * @return a page of {@link ProjectResponse}
     */
    Page<ProjectResponse> getList(String name, Pageable pageable);

    /**
     * Computes the average duration (in days) of projects with status {@code ENCERRADO}.
     * <p>
     * The duration is typically calculated between {@code startDate} and {@code realEndDate}.
     * If there are no closed projects, the implementation may return {@code 0.0} or {@code null}
     * (implementation-defined; document the chosen behavior).
     *
     * @return the average duration in days of closed projects
     */
    Double averageClosedProjectsDurationDays();

    /**
     * Aggregates total budget grouped by {@link ProjectStatus}.
     *
     * @return a list of {@link BudgetByStatus}, one entry per status present
     */
    List<BudgetByStatus> findTotalBudgetByStatus();

    /**
     * Counts projects for a given {@link ProjectStatus}.
     *
     * @param status the status to count
     * @return the number of projects in that status
     * @throws IllegalArgumentException if {@code status} is {@code null}
     */
    Long countByStatus(ProjectStatus status);
}