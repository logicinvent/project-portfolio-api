package br.com.project.portfolio.rest.api.web.dto.request;

import br.com.project.portfolio.rest.api.domain.model.ProjectStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ProjectRequest(
        @NotBlank @Size(max = 255) String name,
        @NotNull LocalDate startDate,
        @NotNull LocalDate plannedEndDate,
        LocalDate realEndDate,
        @NotNull @DecimalMin(value = "0.00") BigDecimal totalBudget,
        @Size(max = 2000) String description,
        @NotNull Integer managerId,
        @NotNull ProjectStatus status,
        UUID id,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}

