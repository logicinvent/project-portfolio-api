package br.com.project.portfolio.rest.api.domain.service;

import br.com.project.portfolio.rest.api.web.dto.request.ProjectMemberRequest;
import br.com.project.portfolio.rest.api.web.dto.response.ProjectMemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service contract for managing project-member allocations.
 * <p>
 * Each allocation is uniquely identified by the composite key
 * {@code (projectId, memberId)}.
 * <p>
 * Typical responsibilities include:
 * <ul>
 *   <li>Allocate a member to a project</li>
 *   <li>Update allocation metadata (e.g., dates, roles)</li>
 *   <li>Remove a member from a project</li>
 *   <li>List or fetch allocations for a given project</li>
 * </ul>
 */
public interface ProjectMemberService {

    /**
     * Creates a new allocation linking a member to a project.
     *
     * @param request payload with the allocation data (e.g., projectId, memberId, allocatedDate, role)
     * @return the created allocation as a response DTO
     * @throws java.util.NoSuchElementException if the referenced project or member does not exist
     * @throws IllegalStateException            if domain rules are violated
     *                                          (e.g., member already allocated, project status forbids changes,
     *                                          member exceeds allowed concurrent projects)
     * @throws IllegalArgumentException         if required fields are missing or invalid
     */
    ProjectMemberResponse save(ProjectMemberRequest request);

    /**
     * Updates an existing allocation identified by {@code projectId} and {@code memberId}.
     *
     * @param projectId the project identifier
     * @param memberId  the member identifier (external or domain id, as defined by the model)
     * @param request   payload with fields to update
     * @return the updated allocation as a response DTO
     * @throws java.util.NoSuchElementException if the allocation is not found
     * @throws IllegalStateException            if the update violates domain rules
     * @throws IllegalArgumentException         if the request is invalid
     */
    ProjectMemberResponse update(UUID projectId, Integer memberId, ProjectMemberRequest request);

    /**
     * Removes an allocation identified by {@code projectId} and {@code memberId}.
     *
     * @param projectId the project identifier
     * @param memberId  the member identifier
     * @throws java.util.NoSuchElementException if the allocation is not found
     * @throws IllegalStateException            if deletion is not permitted due to domain rules
     */
    void delete(UUID projectId, Integer memberId);

    /**
     * Retrieves a single allocation by its composite key.
     *
     * @param projectId the project identifier
     * @param memberId  the member identifier
     * @return the allocation as a response DTO
     * @throws java.util.NoSuchElementException if the allocation is not found
     */
    ProjectMemberResponse findById(UUID projectId, Integer memberId);

    /**
     * Returns a paginated list of allocations for a given project.
     *
     * @param projectId the project identifier
     * @param pageable  pagination and sorting information
     * @return a page of {@link ProjectMemberResponse}
     * @throws java.util.NoSuchElementException if the project is not found (implementation-defined)
     */
    Page<ProjectMemberResponse> getByProject(UUID projectId, Pageable pageable);

    /**
     * Returns a paginated list of allocations for a given project, filtered by member name.
     * <p>
     * Name matching semantics (e.g., contains/starts-with, case sensitivity)
     * are implementation-defined and should be documented in the implementation.
     *
     * @param projectId  the project identifier
     * @param memberName filter for member name
     * @param pageable   pagination and sorting information
     * @return a page of {@link ProjectMemberResponse} matching the filter
     * @throws java.util.NoSuchElementException if the project is not found (implementation-defined)
     * @throws IllegalArgumentException         if {@code memberName} is null or otherwise invalid
     */
    Page<ProjectMemberResponse> getByProject(UUID projectId, String memberName, Pageable pageable);
}