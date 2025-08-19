package br.com.project.portfolio.rest.api.domain.service.impl;

import br.com.project.portfolio.rest.api.domain.model.Member;
import br.com.project.portfolio.rest.api.domain.model.ProjectMember;
import br.com.project.portfolio.rest.api.domain.model.ProjectMemberId;
import br.com.project.portfolio.rest.api.domain.model.ProjectStatus;
import br.com.project.portfolio.rest.api.domain.repository.MemberRepository;
import br.com.project.portfolio.rest.api.domain.repository.ProjectMemberRepository;
import br.com.project.portfolio.rest.api.domain.service.ProjectMemberService;
import br.com.project.portfolio.rest.api.domain.service.ProjectService;
import br.com.project.portfolio.rest.api.shared.exception.MemberFoundInProjectException;
import br.com.project.portfolio.rest.api.shared.exception.NotFoundException;
import br.com.project.portfolio.rest.api.shared.exception.TotalMaxExceededException;
import br.com.project.portfolio.rest.api.shared.util.MembersUtil;
import br.com.project.portfolio.rest.api.web.dto.request.ProjectMemberRequest;
import br.com.project.portfolio.rest.api.web.dto.response.ProjectMemberResponse;
import br.com.project.portfolio.rest.api.web.dto.response.ProjectResponse;
import br.com.project.portfolio.rest.api.web.mapper.ProjectMapper;
import br.com.project.portfolio.rest.api.web.mapper.ProjectMemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static br.com.project.portfolio.rest.api.shared.util.Constants.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository repository;
    private final ProjectMemberMapper projectMemberMapper;
    private final ProjectMapper projectMapper;
    private final MembersUtil membersUtil;
    private final ProjectService projectService;
    private final MemberRepository memberRepository;

    @Override
    public ProjectMemberResponse save(ProjectMemberRequest request) {

        log.info("Starting process to save project member data");

        log.info("Saving/retrieving project member data");
        Member member = membersUtil.getMemberFromProject(request.memberId());

        log.info("Retrieving project data and checking if it exists");
        ProjectResponse project = projectService.findById(request.projectId());

        log.info("Checking if the project already has assigned members");
        List<ProjectMember> projects = repository.findByProject_Id(request.projectId());

        log.info("Checking if the maximum number of allowed members for the project has been exceeded");
        if (projects.size() > 10)
            throw new TotalMaxExceededException(TOTAL_MAX_EXCEEDED);

        log.info("Checking if member already belongs to 3 active projects simultaneously");
        long count = projects.stream()
                .filter(pm -> Objects.equals(pm.getMember().getId(), member.getId()))
                .map(ProjectMember::getProject)
                .filter(p -> !p.getStatus().equals(ProjectStatus.ENCERRADO) && !p.getStatus().equals(ProjectStatus.CANCELADO))
                .limit(3)
                .count();

        if (count > 2)
            throw new TotalMaxExceededException(TOTAL_MAX_EXCEEDED);

        if (projects.size() > 10)
            throw new TotalMaxExceededException(TOTAL_MAX_EXCEEDED);

        log.info("Checking if the member already belongs to this project");
        if (projects.stream().anyMatch(p -> Objects.equals(p.getMember(), member)))
            throw new MemberFoundInProjectException(MEMBER_FOUND_IN_PROJECT);

        log.info("Preparing data to save the member into the project");
        ProjectMember entity = ProjectMember.builder()
                .id(new ProjectMemberId(project.id(), member.getId()))
                .member(member)
                .project(projectMapper.toEntity(project))
                .allocatedDate(request.allocatedDate())
                .build();

        ProjectMemberResponse result = projectMemberMapper.toResponse(repository.save(entity));

        log.info("Finishing process to save project member data");
        return result;
    }

    @Override
    public ProjectMemberResponse update(UUID projectId,
                                        Integer memberId, ProjectMemberRequest request) {
        log.info("Starting process to update project member data");

        log.info("Checking if the member has permission");
        Member member = membersUtil.getMemberFromProject(request.memberId());

        log.info("Checking if the member already belongs to the project (and vice versa)");
        ProjectMember entity = repository.findById(new ProjectMemberId(projectId, member.getId()))
                .orElseThrow(() -> new NotFoundException(GENERIC_ERROR));

        log.info("Checking if the project was changed");
        if (!entity.getProject().getId().equals(request.projectId()))
            entity.setProject(
                    projectMapper.toEntity(projectService.findById(request.projectId())));

        log.info("Checking if the member was changed");
        if (!entity.getMember().getId().equals(member.getId()))
            entity.setMember(member);

        entity.setId(new ProjectMemberId(request.projectId(), entity.getMember().getId()));

        return projectMemberMapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(UUID projectId,
                       Integer memberId) {

        log.info("Starting process to remove member from project");

        Optional<Member> optional = memberRepository.findByExternalId(memberId);

        if (optional.isEmpty())
            throw new MemberFoundInProjectException(MEMBER_FOUND_IN_PROJECT);

        Member member = optional.get();

        log.info("Checking if member exists in the project before deletion");
        if (!repository.existsById(new ProjectMemberId(projectId, member.getId())))
            throw new NotFoundException(GENERIC_ERROR);

        log.info("Deleting member from project");
        repository.deleteById(new ProjectMemberId(projectId, member.getId()));

        log.info("Member successfully removed from project");
    }

    @Override
    public ProjectMemberResponse findById(UUID projectId,
                                          Integer memberId) {
        log.info("Retrieving member data from project by ID");

        Optional<Member> optional = memberRepository.findByExternalId(memberId);

        if (optional.isEmpty())
            throw new MemberFoundInProjectException(MEMBER_FOUND_IN_PROJECT);

        Member member = optional.get();

        return repository.findById(new ProjectMemberId(projectId, member.getId()))
                .map(projectMemberMapper::toResponse)
                .orElseThrow(() -> new NotFoundException(GENERIC_ERROR));
    }

    @Override
    public Page<ProjectMemberResponse> getByProject(UUID projectId, Pageable pageable) {
        log.info("Listing all members assigned to project");
        return repository.findByProject_Id(projectId, pageable)
                .map(projectMemberMapper::toResponse);
    }

    @Override
    public Page<ProjectMemberResponse> getByProject(UUID projectId, String memberName, Pageable pageable) {
        log.info("Listing project members with optional name filter");
        if (memberName == null || memberName.isBlank()) {
            return getByProject(projectId, pageable);
        }
        return repository.findByProject_IdAndMember_NameContainingIgnoreCase(projectId, memberName.trim(), pageable)
                .map(projectMemberMapper::toResponse);
    }
}
