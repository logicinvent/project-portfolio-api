package br.com.project.portfolio.rest.api.web.dto.response;

import br.com.project.portfolio.rest.api.domain.model.Member;
import br.com.project.portfolio.rest.api.domain.model.ProjectRiskLevel;
import br.com.project.portfolio.rest.api.domain.model.ProjectStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ProjectResponse(
        UUID id,
        String name,
        LocalDate startDate,
        LocalDate plannedEndDate,
        LocalDate realEndDate,
        BigDecimal totalBudget,
        String description,
        Member manager,
        ProjectStatus status,
        ProjectRiskLevel riskLevel,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}

