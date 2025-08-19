package br.com.project.portfolio.rest.api.domain.repository;

import br.com.project.portfolio.rest.api.domain.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

    Optional<Member> findByExternalId(Integer externalId);
    Page<Member> findByNameContainingIgnoreCase(String name, Pageable pageable);
    @Query("select count(distinct pm.member.id) from ProjectMember pm")
    long countUniqueMembersAllocated();

}
