package br.com.project.portfolio.rest.api.domain.repository;

import br.com.project.portfolio.rest.api.domain.model.ProjectMember;
import br.com.project.portfolio.rest.api.domain.model.ProjectMemberId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {

    Page<ProjectMember> findByProject_Id(UUID projectId, Pageable pageable);
    List<ProjectMember> findByProject_Id(UUID projectId);
    Page<ProjectMember> findByProject_IdAndMember_NameContainingIgnoreCase(UUID projectId,
                                                                           String memberName, Pageable pageable);
}
