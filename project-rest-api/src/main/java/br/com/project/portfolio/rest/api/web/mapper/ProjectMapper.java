package br.com.project.portfolio.rest.api.web.mapper;

import br.com.project.portfolio.rest.api.domain.model.Project;
import br.com.project.portfolio.rest.api.shared.util.ProjectUtils;
import br.com.project.portfolio.rest.api.web.dto.request.ProjectRequest;
import br.com.project.portfolio.rest.api.web.dto.response.ProjectResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = ProjectUtils.class)
public interface ProjectMapper {

    @Mapping(target = "riskLevel", source = "entity", qualifiedByName = "classifyRisk")
    ProjectResponse toResponse(Project entity);

    Project toEntity(ProjectRequest request);
    Project toEntity(ProjectResponse request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(ProjectRequest request, @MappingTarget Project entity);
}

