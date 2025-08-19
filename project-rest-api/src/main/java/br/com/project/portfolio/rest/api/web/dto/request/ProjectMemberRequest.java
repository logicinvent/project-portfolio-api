package br.com.project.portfolio.rest.api.web.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.UUID;

public record ProjectMemberRequest(
        @NotNull UUID projectId,
        @NotNull Integer memberId,
        @NotNull @PastOrPresent LocalDate allocatedDate
) {}
