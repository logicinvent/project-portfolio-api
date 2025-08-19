package br.com.project.portfolio.mock.member.api.web.controller.impl;

import br.com.project.portfolio.mock.member.api.shared.enums.EmploymentContract;
import br.com.project.portfolio.mock.member.api.shared.enums.Occupation;
import br.com.project.portfolio.mock.member.api.shared.exception.NotFoundException;
import br.com.project.portfolio.mock.member.api.web.controller.MockMembersController;
import br.com.project.portfolio.mock.member.api.web.dto.EmploymentContractDto;
import br.com.project.portfolio.mock.member.api.web.dto.ExternalMemberRequest;
import br.com.project.portfolio.mock.member.api.web.dto.ExternalMemberResponse;
import br.com.project.portfolio.mock.member.api.web.dto.OccupationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/members")
public class MockMembersControllerImpl implements MockMembersController {

    private final Map<Integer, ExternalMemberResponse> store = new ConcurrentHashMap<>();

    @PostMapping
    public ResponseEntity<ExternalMemberResponse> save(@RequestBody ExternalMemberRequest req) {
        Integer id =  Math.abs(UUID.randomUUID().hashCode());

        log.info("Verificando se o cargo é valido");
        var occupation =  Arrays.stream(Occupation.values())
                .filter(c -> c.getId().equals(req.occupationId()))
                .map(oc -> {return new OccupationDto(oc.getId(), oc.getOccupation());})
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Invalid occupation id: " + req.occupationId()));

        log.info("Verificando se o contrato é valido");
        var employmentContract =  Arrays.stream(EmploymentContract.values())
                .filter(c -> c.getId().equals(req.employmentContractId()))
                .map(oc -> {return new EmploymentContractDto(oc.getId(), oc.getContract());})
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Invalid contract id: " + req.employmentContractId()));


        var out = new ExternalMemberResponse(id, req.name(), occupation, employmentContract);
        store.put(id, out);
        return ResponseEntity.status(HttpStatus.CREATED).body(out);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExternalMemberResponse> getById(@PathVariable Integer id) {

        var out = store.get(id);

        if (Objects.isNull(out))
            throw new NotFoundException("Member not found");

        return ResponseEntity.ok(out);
    }

    @GetMapping
    public ResponseEntity<List<ExternalMemberResponse>> list() {
        return ResponseEntity.ok(
                store.values().stream().toList()
        );
    }
}