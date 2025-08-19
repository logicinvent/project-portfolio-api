package br.com.project.portfolio.rest.api.web.controller.api;

import br.com.project.portfolio.rest.api.web.dto.request.ProjectMemberRequest;
import br.com.project.portfolio.rest.api.web.dto.response.ProjectMemberResponse;
import br.com.project.portfolio.rest.api.web.dto.response.TransferObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

/**
 * API contract for managing project members.
 * <p>
 * This interface documents payloads and responses for consumers.
 * HTTP verbs and route mappings are declared in the implementing controller.
 */
@Tag(name = "Project Members", description = "Manage member allocations within projects.")
public interface ProjectMemberControllerApi {

    /**
     * Creates a new project-member allocation.
     *
     * @param request allocation data (project, member, dates, role, etc.)
     * @return the created allocation wrapped in a {@link TransferObject}
     */
    @Operation(
            summary = "Create project-member allocation",
            description = "Creates a new allocation of a member to a project."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransferObject.class))),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Related entity not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict (e.g., duplicate allocation)", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<TransferObject<ProjectMemberResponse>> save(
            @Parameter(description = "Allocation payload", required = true)
            ProjectMemberRequest request
    );

    /**
     * Updates an existing project-member allocation.
     *
     * @param projectId project identifier
     * @param memberId  member external identifier
     * @param request   allocation data to update
     * @return the updated allocation wrapped in a {@link TransferObject}
     */
    @Operation(
            summary = "Update project-member allocation",
            description = "Updates an existing allocation for the given project and member."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransferObject.class))),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Allocation not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<TransferObject<ProjectMemberResponse>> update(
            @Parameter(in = ParameterIn.PATH, description = "Project ID", required = true) UUID projectId,
            @Parameter(in = ParameterIn.PATH, description = "Member external ID", required = true) Integer memberId,
            @Parameter(description = "Allocation payload", required = true) ProjectMemberRequest request
    );

    /**
     * Deletes a project-member allocation.
     *
     * @param projectId project identifier
     * @param memberId  member external identifier
     */
    @Operation(
            summary = "Delete project-member allocation",
            description = "Deletes the allocation for the given project and member."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content (deleted)"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Allocation not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict (business rule violation)", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    void delete(
            @Parameter(in = ParameterIn.PATH, description = "Project ID", required = true) UUID projectId,
            @Parameter(in = ParameterIn.PATH, description = "Member external ID", required = true) Integer memberId
    );

    /**
     * Retrieves a project-member allocation by composite key.
     *
     * @param projectId project identifier
     * @param memberId  member external identifier
     * @return the allocation wrapped in a {@link TransferObject}
     */
    @Operation(
            summary = "Get allocation by IDs",
            description = "Retrieves the allocation identified by projectId and memberId."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransferObject.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Allocation not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<TransferObject<ProjectMemberResponse>> findById(
            @Parameter(in = ParameterIn.PATH, description = "Project ID", required = true) UUID projectId,
            @Parameter(in = ParameterIn.PATH, description = "Member external ID", required = true) Integer memberId
    );

    /**
     * Lists allocations for a given project with optional member name filtering and pagination.
     *
     * @param projectId project identifier
     * @param name      optional filter by member name (contains/like)
     * @param pageable  pagination and sorting parameters
     * @return a paged list of allocations wrapped in a {@link TransferObject}
     */
    @Operation(
            summary = "List allocations by project",
            description = "Returns allocations for the specified project, optionally filtered by member name."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransferObject.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Project not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<TransferObject<List<ProjectMemberResponse>>> getByProject(
            @Parameter(in = ParameterIn.PATH, description = "Project ID", required = true) UUID projectId,
            @Parameter(in = ParameterIn.QUERY, description = "Filter by member name (optional)") String name,
            @ParameterObject Pageable pageable
    );
}