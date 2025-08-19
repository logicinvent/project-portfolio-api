package br.com.project.portfolio.rest.api.infrastructure.external;

import br.com.project.portfolio.rest.api.infrastructure.external.dto.ExternalMemberRequest;
import br.com.project.portfolio.rest.api.infrastructure.external.dto.ExternalMemberResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "mockMembersClient",
        url = "${clients.mock-members.base-url}",
        path = "/api/members"
)
public interface MockMembersClient {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ExternalMemberResponse>> list();

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ExternalMemberResponse> getById(@PathVariable Integer id);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ExternalMemberResponse> save(@RequestBody ExternalMemberRequest request);
}
