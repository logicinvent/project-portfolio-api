package br.com.project.portfolio.mock.member.api.web.dto;

public record ExternalMemberRequest(
        String name,
        Integer employmentContractId,
        Integer occupationId) {}
