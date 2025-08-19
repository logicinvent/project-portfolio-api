package br.com.project.portfolio.mock.member.api.web.controller;

import br.com.project.portfolio.mock.member.api.web.dto.OccupationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * API contract for Mock Occupations.
 * <p>Defines payloads and responses for consumers.
 * Implementing controllers should declare HTTP verbs and route mappings.</p>
 */
@Tag(name = "Mock Occupations", description = "Read mock occupations catalog.")
public interface MockOccupationsController {

    /**
     * Retrieves an occupation by its ID.
     *
     * @param id the occupation identifier
     * @return the occupation entry
     */
    @Operation(summary = "Get occupation by ID", description = "Returns a single occupation for the given ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OccupationDto.class))),
            @ApiResponse(responseCode = "404", description = "Occupation not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<OccupationDto> getById(
            @Parameter(in = ParameterIn.PATH, description = "Occupation ID", required = true, example = "10")
            Integer id
    );

    /**
     * Lists all occupations.
     *
     * @return the list of occupations
     */
    @Operation(summary = "List occupations", description = "Returns all available occupations.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OccupationDto.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<List<OccupationDto>> list();
}