package br.com.project.portfolio.rest.api.infrastructure.external;

import br.com.project.portfolio.rest.api.infrastructure.external.dto.OccupationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "occupationsClient",
        url = "${clients.mock-members.base-url}",
        path = "/api/occupations"
)
public interface MockOccupationsClient {

    @GetMapping
    ResponseEntity<List<OccupationDto>> list();

    @GetMapping("/{id}")
    ResponseEntity<OccupationDto> getById(@PathVariable Integer id);
}