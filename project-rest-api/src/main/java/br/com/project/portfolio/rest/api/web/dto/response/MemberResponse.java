package br.com.project.portfolio.rest.api.web.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record MemberResponse(
        UUID id,
        String externalId,
        String name,
        UUID occupationId,
        String occupationName,
        String occupationCode,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
