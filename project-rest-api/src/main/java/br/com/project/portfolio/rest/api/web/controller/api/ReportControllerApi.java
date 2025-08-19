package br.com.project.portfolio.rest.api.web.controller.api;

import br.com.project.portfolio.rest.api.domain.model.BudgetByStatus;
import br.com.project.portfolio.rest.api.domain.model.ProjectStatus;
import br.com.project.portfolio.rest.api.web.dto.response.TransferObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * API contract for project reporting resources.
 * <p>
 * This interface documents the responses and payloads exposed by the reporting endpoints.
 * HTTP verbs and route mappings are defined in the implementing controller.
 */
@Tag(name = "Reports", description = "Aggregated metrics and KPIs for projects.")
public interface ReportControllerApi {

    /**
     * Returns the number of unique members allocated across all projects.
     *
     * @return a {@link TransferObject} wrapping the unique member count
     */
    @Operation(
            summary = "Unique allocated members",
            description = "Returns the total number of distinct members allocated across all projects."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransferObject.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<TransferObject<Long>> countUniqueMembersAllocated();

    /**
     * Returns the average duration (in days) of closed projects.
     *
     * @return a {@link TransferObject} wrapping the average number of days for projects with status {@code ENCERRADO}
     */
    @Operation(
            summary = "Average duration of closed projects (days)",
            description = "Calculates the average duration in days for projects that are closed."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransferObject.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<TransferObject<Double>> averageClosedProjectsDurationDays();

    /**
     * Returns the total budget aggregated by project status.
     *
     * @return a {@link TransferObject} wrapping a list of ({@code status}, {@code totalBudget}) pairs
     */
    @Operation(
            summary = "Total budget by status",
            description = "Aggregates the total budget grouped by project status."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransferObject.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<TransferObject<List<BudgetByStatus>>> findTotalBudgetByStatus();

    /**
     * Returns the number of projects for a given status.
     *
     * @param status the project status to count
     * @return a {@link TransferObject} wrapping the count for the provided status
     */
    @Operation(
            summary = "Project count by status",
            description = "Returns the number of projects that match the provided status."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransferObject.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    ResponseEntity<TransferObject<Long>> countByStatus(
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Project status to filter by",
                    required = true,
                    schema = @Schema(implementation = ProjectStatus.class)
            )
            ProjectStatus status
    );
}