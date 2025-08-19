package br.com.project.portfolio.rest.api.infrastructure.external.dto;

public record ExternalMemberResponse(
        Integer id,
        String name,
        OccupationDto occupation,
        EmploymentContractDto employmentContract
) {}

