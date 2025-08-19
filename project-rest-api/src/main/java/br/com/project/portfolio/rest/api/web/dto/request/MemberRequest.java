package br.com.project.portfolio.rest.api.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record MemberRequest(
        @NotBlank @Size(max = 255) String externalId,
        @NotBlank @Size(max = 255) String name,
        @NotNull UUID occupationId
) {}
