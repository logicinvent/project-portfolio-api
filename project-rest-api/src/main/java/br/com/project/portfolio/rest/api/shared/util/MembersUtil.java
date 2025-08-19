package br.com.project.portfolio.rest.api.shared.util;

import br.com.project.portfolio.rest.api.domain.model.Member;
import br.com.project.portfolio.rest.api.domain.repository.MemberRepository;
import br.com.project.portfolio.rest.api.infrastructure.external.MockMembersClient;
import br.com.project.portfolio.rest.api.infrastructure.external.dto.ExternalMemberResponse;
import br.com.project.portfolio.rest.api.shared.exception.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static br.com.project.portfolio.rest.api.shared.util.Constants.*;

/**
 * Utility component that centralizes member-related helper operations, such as:
 * <ul>
 *   <li>Validating an external member's occupation and employment contract against business rules;</li>
 *   <li>Resolving a {@link Member} by external ID from the local repository, or fetching it from an external
 *       service (Mock Members API) when not present, validating it, persisting it, and returning the saved entity.</li>
 * </ul>
 *
 * <p>Configuration:
 * <ul>
 *   <li>{@code clients.mock-occupation}: required occupation ID the member must have;</li>
 *   <li>{@code clients.mock-contract}: required employment contract ID the member must have.</li>
 * </ul>
 *
 * <p>This class performs logging for observability and throws {@link NotFoundException} when validations fail or the
 * external API responds with an error status.</p>
 */
@Log4j2
@Component
public class MembersUtil {

    private final MemberRepository memberRepository;
    private final MockMembersClient mockMembersClient;

    @Value("${clients.mock-occupation}")
    private Integer occupationId;

    @Value("${clients.mock-contract}")
    private Integer contractId;

    /**
     * Creates a new {@code MembersUtil}.
     *
     * @param memberRepository  repository used to resolve and persist {@link Member} entities
     * @param mockMembersClient client used to fetch member data from the Mock Members API when not available locally
     */
    public MembersUtil(MemberRepository memberRepository, MockMembersClient mockMembersClient) {
        this.memberRepository = memberRepository;
        this.mockMembersClient = mockMembersClient;
    }

    /**
     * Validates whether the provided external member has the required occupation.
     *
     * @param externalMemberResponse the payload returned by the external members service
     * @throws NotFoundException if the payload or occupation is missing, or if the occupation ID does not match
     *                           the configured {@code clients.mock-occupation} value
     */
    public void validateManagerOccupation(ExternalMemberResponse externalMemberResponse) {
        log.info("[MembersUtil] Validating required occupation for member");
        if (Objects.isNull(externalMemberResponse) || Objects.isNull(externalMemberResponse.occupation())) {
            log.error("[MembersUtil] Occupation is missing on external member payload");
            throw new NotFoundException(OCCUPATION_ERROR_IS_NULL);
        }
        if (!externalMemberResponse.occupation().id().equals(occupationId)) {
            log.error("[MembersUtil] Member occupation not permitted. expectedId={}, actualId={}",
                    occupationId, externalMemberResponse.occupation().id());
            throw new NotFoundException(OCCUPATION_ERROR);
        }
    }

    /**
     * Validates whether the provided external member has the required employment contract.
     *
     * @param externalMemberResponse the payload returned by the external members service
     * @throws NotFoundException if the payload or contract is missing, or if the contract ID does not match
     *                           the configured {@code clients.mock-contract} value
     */
    public void validateManagerContract(ExternalMemberResponse externalMemberResponse) {
        log.info("[MembersUtil] Validating required employment contract for member");
        if (Objects.isNull(externalMemberResponse) || Objects.isNull(externalMemberResponse.employmentContract())) {
            log.error("[MembersUtil] Employment contract is missing on external member payload");
            throw new NotFoundException(CONTRACT_ERROR_IS_NULL);
        }
        if (!externalMemberResponse.employmentContract().id().equals(contractId)) {
            log.error("[MembersUtil] Member contract not permitted. expectedId={}, actualId={}",
                    contractId, externalMemberResponse.employmentContract().id());
            throw new NotFoundException(CONTRACT_ERROR);
        }
    }

    /**
     * Resolves a local {@link Member} by the given external ID. If the member does not exist locally, it will:
     * <ol>
     *   <li>Fetch the member from the Mock Members API;</li>
     *   <li>Validate occupation and employment contract against business rules;</li>
     *   <li>Persist a new {@link Member} entity with the external information;</li>
     *   <li>Return the saved entity.</li>
     * </ol>
     *
     * @param externalId the external identifier of the member
     * @return the resolved or newly persisted {@link Member}
     * @throws NotFoundException if the external service responds with an error status, or if occupation/contract
     *                           validations fail
     */
    public Member getMemberFromProject(Integer externalId) {
        log.info("[MembersUtil] Checking if member exists locally. externalId={}", externalId);

        return memberRepository.findByExternalId(externalId).orElseGet(() -> {
            log.info("[MembersUtil] Fetching member from Mock Members API. externalId={}", externalId);
            ResponseEntity<ExternalMemberResponse> response = mockMembersClient.getById(externalId);

            if (response.getStatusCode().isError()) {
                log.error("[MembersUtil] Mock Members API returned error. status={}, externalId={}",
                        response.getStatusCode(), externalId);
                throw new NotFoundException(MEMBER_ERROR + externalId);
            }

            ExternalMemberResponse body = response.getBody();
            log.info("[MembersUtil] Validating occupation and contract for external member. externalId={}", externalId);
            validateManagerOccupation(body);
            validateManagerContract(body);

            log.info("[MembersUtil] Mapping external member to entity. name={}, externalId={}", body.name(), externalId);
            Member toSave = Member.builder()
                    .name(body.name())
                    .externalId(externalId)
                    .build();

            Member saved = memberRepository.save(toSave);
            log.info("[MembersUtil] Member persisted successfully. id={}, externalId={}", saved.getId(), externalId);
            return saved;
        });
    }
}