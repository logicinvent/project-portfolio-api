package br.com.project.portfolio.mock.member.api.web.controller;

import br.com.project.portfolio.mock.member.api.web.dto.ExternalMemberRequest;
import br.com.project.portfolio.mock.member.api.web.dto.ExternalMemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * API contract for Mock Members.
 * <p>
 * This interface documents payloads and responses for consumers.
 * HTTP verbs and route mappings are declared in the implementing controller.
 */
@Tag(name = "Mock Members", description = "Read and manage mock members.")
public interface MockMembersController {

    /**
     * Creates a mock member.
     *
     * @param req the member payload
     * @return the created member
     */
    @Operation(summary = "Create member", description = "Creates a mock member.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExternalMemberResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<ExternalMemberResponse> save(
            @RequestBody(description = "Member payload", required = true,
                    content = @Content(schema = @Schema(implementation = ExternalMemberRequest.class)))
            ExternalMemberRequest req
    );

    /**
     * Retrieves a mock member by its ID.
     *
     * @param id the member identifier
     * @return the member data
     */
    @Operation(summary = "Get member by ID", description = "Returns a single member for the given ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExternalMemberResponse.class))),
            @ApiResponse(responseCode = "404", description = "Member not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<ExternalMemberResponse> getById(
            @Parameter(in = ParameterIn.PATH, description = "Member ID", required = true, example = "123")
            Integer id
    );

    /**
     * Lists all mock members.
     *
     * @return the list of members
     */
    @Operation(summary = "List members", description = "Returns all mock members.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ExternalMemberResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<List<ExternalMemberResponse>> list();
}