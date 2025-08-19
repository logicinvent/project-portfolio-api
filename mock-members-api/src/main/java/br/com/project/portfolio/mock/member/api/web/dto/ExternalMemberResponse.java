package br.com.project.portfolio.mock.member.api.web.dto;

public record ExternalMemberResponse(
        Integer id,
        String name,
        OccupationDto occupation,
        EmploymentContractDto employmentContract
) {}


