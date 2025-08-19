package br.com.project.portfolio.rest.api.web.mapper;

import br.com.project.portfolio.rest.api.domain.model.Member;
import br.com.project.portfolio.rest.api.infrastructure.external.dto.ExternalMemberResponse;
import br.com.project.portfolio.rest.api.web.dto.request.MemberRequest;
import br.com.project.portfolio.rest.api.web.dto.response.MemberResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

    Member toEntity(MemberRequest request);
    Member toEntity(ExternalMemberResponse request);
    MemberResponse toResponse(Member entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(MemberRequest request, @MappingTarget Member entity);

}
