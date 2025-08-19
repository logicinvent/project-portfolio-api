package br.com.project.portfolio.rest.api.domain.service;

import br.com.project.portfolio.rest.api.web.dto.request.MemberRequest;
import br.com.project.portfolio.rest.api.web.dto.response.MemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service contract for managing members (people) in the Project Portfolio domain.
 * <p>
 * Typical responsibilities include:
 * <ul>
 *   <li>Create, update, delete and retrieve members</li>
 *   <li>Paginated listing with optional name filtering</li>
 *   <li>Domain-level metrics such as counting unique allocated members</li>
 * </ul>
 */
public interface MemberService {

    /**
     * Creates a new member.
     *
     * @param request DTO with the member data to be persisted
     * @return the created member as a response DTO
     * @throws IllegalArgumentException if required fields are missing or invalid
     * @throws IllegalStateException    if domain rules are violated (e.g., duplicate external identifier)
     */
    MemberResponse save(MemberRequest request);

    /**
     * Updates an existing member identified by {@code id}.
     *
     * @param id      the member ID
     * @param request DTO with fields to update
     * @return the updated member as a response DTO
     * @throws java.util.NoSuchElementException if the member is not found
     * @throws IllegalArgumentException         if the request is invalid
     * @throws IllegalStateException            if the update violates domain rules
     */
    MemberResponse update(UUID id, MemberRequest request);

    /**
     * Deletes a member by ID.
     *
     * @param id the member ID
     * @throws java.util.NoSuchElementException if the member is not found
     * @throws IllegalStateException            if deletion is not permitted (e.g., member allocated to active projects)
     */
    void delete(UUID id);

    /**
     * Retrieves a member by ID.
     *
     * @param id the member ID
     * @return the member as a response DTO
     * @throws java.util.NoSuchElementException if the member is not found
     */
    MemberResponse findById(UUID id);

    /**
     * Returns a paginated list of members, optionally filtered by name
     * (matching semantics are implementation-defined).
     *
     * @param name     optional filter for member name; when {@code null}, returns all
     * @param pageable pagination and sorting information
     * @return a page of {@link MemberResponse}
     */
    Page<MemberResponse> getList(String name, Pageable pageable);

    /**
     * Counts the number of unique members currently allocated to at least one project.
     * <p>
     * The definition of "allocated" and the set of project statuses considered
     * (e.g., excluding {@code ENCERRADO}/{@code CANCELADO}) are implementation-defined
     * and should be documented in the implementation.
     *
     * @return the total count of distinct allocated members
     */
    long countUniqueMembersAllocated();

}