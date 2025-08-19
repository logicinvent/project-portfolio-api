package br.com.project.portfolio.mock.member.api.web.controller;

import br.com.project.portfolio.mock.member.api.web.dto.EmploymentContractDto;
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
 * API contract for Employment Contracts (mock service).
 * <p>
 * This interface documents payloads and responses for consumers.
 * HTTP verbs and route mappings are declared in the implementing controller.
 */
@Tag(name = "Employment Contracts (Mock)", description = "Read employment contracts from the mock service.")
public interface MockEmploymentsContractController {

    /**
     * Retrieves an employment contract by its numeric identifier.
     *
     * @param id contract identifier
     * @return the contract data
     */
    @Operation(summary = "Get employment contract by ID",
            description = "Returns a single employment contract for the given ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmploymentContractDto.class))),
            @ApiResponse(responseCode = "404", description = "Contract not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<EmploymentContractDto> getById(
            @Parameter(in = ParameterIn.PATH, description = "Employment contract ID", required = true)
            Integer id
    );

    /**
     * Lists all employment contracts available in the mock service.
     *
     * @return the list of contracts
     */
    @Operation(summary = "List employment contracts",
            description = "Returns all employment contracts available.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EmploymentContractDto.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<List<EmploymentContractDto>> list();
}