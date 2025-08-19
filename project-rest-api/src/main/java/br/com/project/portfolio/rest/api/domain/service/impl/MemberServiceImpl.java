package br.com.project.portfolio.rest.api.domain.service.impl;

import br.com.project.portfolio.rest.api.domain.model.Member;
import br.com.project.portfolio.rest.api.domain.repository.MemberRepository;
import br.com.project.portfolio.rest.api.domain.service.MemberService;
import br.com.project.portfolio.rest.api.shared.exception.MemberNotFoundException;
import br.com.project.portfolio.rest.api.web.dto.request.MemberRequest;
import br.com.project.portfolio.rest.api.web.dto.response.MemberResponse;
import br.com.project.portfolio.rest.api.web.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

import static br.com.project.portfolio.rest.api.shared.util.Constants.MEMBER_NOT_FOUND;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;
    private final MemberMapper mapper;

    @Override
    public MemberResponse save(MemberRequest request) {
        log.info("Starting process to save member data");
        Member entity = mapper.toEntity(request);
        MemberResponse response = mapper.toResponse(repository.save(entity));
        log.info("Member successfully saved with id={}", response.id());
        return response;
    }

    @Override
    public MemberResponse update(UUID id, MemberRequest request) {
        log.info("Starting process to update member with id={}", id);
        Member entity = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Member not found for id={}", id);
                    return new MemberNotFoundException(MEMBER_NOT_FOUND);
                });

        mapper.updateEntityFromRequest(request, entity);
        MemberResponse response = mapper.toResponse(repository.save(entity));
        log.info("Member successfully updated id={}", response.id());
        return response;
    }

    @Override
    public void delete(UUID id) {
        log.info("Starting process to delete member id={}", id);
        if (!repository.existsById(id)) {
            log.warn("Member not found for deletion id={}", id);
            throw new MemberNotFoundException(MEMBER_NOT_FOUND);
        }
        repository.deleteById(id);
        log.info("Member successfully deleted id={}", id);
    }

    @Override
    public MemberResponse findById(UUID id) {
        log.info("Retrieving member by id={}", id);
        return repository.findById(id)
                .map(member -> {
                    log.debug("Member found id={}", id);
                    return mapper.toResponse(member);
                })
                .orElseThrow(() -> {
                    log.warn("Member not found id={}", id);
                    return new MemberNotFoundException(MEMBER_NOT_FOUND);
                });
    }

    @Override
    public Page<MemberResponse> getList(String name, Pageable pageable) {
        log.info("Listing members with{} name filter",
                (Objects.isNull(name) || name.isBlank()) ? "out" : "");
        if (Objects.isNull(name) || name.isBlank()) {
            return repository.findAll(pageable).map(mapper::toResponse);
        }
        return repository.findByNameContainingIgnoreCase(name.trim(), pageable)
                .map(mapper::toResponse);
    }

    @Override
    public long countUniqueMembersAllocated() {
        log.info("Counting unique allocated members");
        long count = repository.countUniqueMembersAllocated();
        log.debug("Unique allocated members count={}", count);
        return count;
    }
}
