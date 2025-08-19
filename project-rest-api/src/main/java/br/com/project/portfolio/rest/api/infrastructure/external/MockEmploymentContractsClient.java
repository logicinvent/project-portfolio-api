package br.com.project.portfolio.rest.api.infrastructure.external;

import br.com.project.portfolio.rest.api.infrastructure.external.dto.EmploymentContractDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "employmentContractsClient",
        url = "${clients.mock-members.base-url}",
        path = "/api/employments-contract"
)
public interface MockEmploymentContractsClient {

    @GetMapping
    ResponseEntity<List<EmploymentContractDto>> list();

    @GetMapping("/{id}")
    ResponseEntity<EmploymentContractDto> getById(@PathVariable Integer id);
}