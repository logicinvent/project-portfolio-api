package br.com.project.portfolio.mock.member.api.web.controller.impl;

import br.com.project.portfolio.mock.member.api.shared.enums.EmploymentContract;
import br.com.project.portfolio.mock.member.api.shared.exception.NotFoundException;
import br.com.project.portfolio.mock.member.api.web.controller.MockEmploymentsContractController;
import br.com.project.portfolio.mock.member.api.web.dto.EmploymentContractDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/employments-contract")
public class MockEmploymentsContractControllerImpl implements MockEmploymentsContractController {

    @GetMapping("/{id}")
    public ResponseEntity<EmploymentContractDto> getById(@PathVariable Integer id) {

        if (Objects.isNull(id))
            throw new NotFoundException("Contract not found");

        return ResponseEntity.ok(
                Arrays.stream(EmploymentContract.values())
                        .filter(c -> c.getId().equals(id))
                        .map(ec -> new EmploymentContractDto(ec.getId(), ec.getContract()))
                        .findFirst()
                        .orElseThrow(() -> new NotFoundException("Invalid contract id: " + id))
        );
    }

    @GetMapping
    public ResponseEntity<List<EmploymentContractDto>> list() {
        return ResponseEntity.ok(
                Arrays.stream(EmploymentContract.values())
                        .map(ec -> new EmploymentContractDto(ec.getId(), ec.getContract())).toList()
        );
    }

}