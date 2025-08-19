package br.com.project.portfolio.rest.api.web.controller.api;

import br.com.project.portfolio.rest.api.web.dto.request.ProjectRequest;
import br.com.project.portfolio.rest.api.web.dto.response.ProjectResponse;
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
 * API contract for managing Projects.
 * <p>
 * This interface documents payloads and responses for consumers.
 * HTTP verbs and route mappings are declared in the implementing controller.
 */
@Tag(name = "Projects", description = "Manage projects and their lifecycle.")
public interface ProjectControllerApi {

    /**
     * Creates a new project.
     *
     * @param request project payload
     * @return the created project wrapped in a {@link TransferObject}
     */
    @Operation(summary = "Create project", description = "Creates a new project with the provided data.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransferObject.class))),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict (business rule violation)", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<TransferObject<ProjectResponse>> save(
            @Parameter(description = "Project payload", required = true)
            ProjectRequest request
    );

    /**
     * Updates an existing project by ID.
     *
     * @param id      project identifier
     * @param request project payload to update
     * @return the updated project wrapped in a {@link TransferObject}
     */
    @Operation(summary = "Update project", description = "Updates an existing project identified by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransferObject.class))),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Project not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict (business rule violation)", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<TransferObject<ProjectResponse>> update(
            @Parameter(in = ParameterIn.PATH, description = "Project ID", required = true) UUID id,
            @Parameter(description = "Project payload", required = true) ProjectRequest request
    );

    /**
     * Deletes a project by ID.
     *
     * @param id project identifier
     */
    @Operation(summary = "Delete project", description = "Deletes a project identified by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content (deleted)"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Project not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict (business rule violation)", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    void delete(
            @Parameter(in = ParameterIn.PATH, description = "Project ID", required = true) UUID id
    );

    /**
     * Retrieves a project by ID.
     *
     * @param id project identifier
     * @return the project wrapped in a {@link TransferObject}
     */
    @Operation(summary = "Get project by ID", description = "Retrieves a project by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransferObject.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Project not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<TransferObject<ProjectResponse>> findById(
            @Parameter(in = ParameterIn.PATH, description = "Project ID", required = true) UUID id
    );

    /**
     * Lists projects with optional name filtering and pagination.
     *
     * @param name     optional filter by project name (contains/like)
     * @param pageable pagination and sorting parameters
     * @return a paged list of projects wrapped in a {@link TransferObject}
     */
    @Operation(summary = "List projects", description = "Returns projects optionally filtered by name, with pagination.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransferObject.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<TransferObject<List<ProjectResponse>>> getList(
            @Parameter(in = ParameterIn.QUERY, description = "Filter by project name (optional)") String name,
            @ParameterObject Pageable pageable
    );
}