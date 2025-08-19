package br.com.project.portfolio.rest.api.infrastructure.external.dto;

public record ExternalMemberRequest(
        String name,
        Integer employmentContractId,
        Integer occupationId
) {}
