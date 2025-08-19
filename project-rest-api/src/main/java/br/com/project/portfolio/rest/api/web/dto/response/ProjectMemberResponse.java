package br.com.project.portfolio.rest.api.web.dto.response;

import br.com.project.portfolio.rest.api.domain.model.Member;
import br.com.project.portfolio.rest.api.domain.model.Project;
import br.com.project.portfolio.rest.api.domain.model.ProjectMemberId;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record ProjectMemberResponse(
        ProjectMemberId id,
        Project project,
        Member member,
        LocalDate allocatedDate,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
