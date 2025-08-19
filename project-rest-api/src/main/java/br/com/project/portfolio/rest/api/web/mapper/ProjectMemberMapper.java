package br.com.project.portfolio.rest.api.web.mapper;

import br.com.project.portfolio.rest.api.domain.model.ProjectMember;
import br.com.project.portfolio.rest.api.web.dto.response.ProjectMemberResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMemberMapper {

    ProjectMemberResponse toResponse(ProjectMember entity);

}
