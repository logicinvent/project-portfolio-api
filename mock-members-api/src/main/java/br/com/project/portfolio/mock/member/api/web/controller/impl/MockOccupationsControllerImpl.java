package br.com.project.portfolio.mock.member.api.web.controller.impl;

import br.com.project.portfolio.mock.member.api.shared.enums.Occupation;
import br.com.project.portfolio.mock.member.api.shared.exception.NotFoundException;
import br.com.project.portfolio.mock.member.api.web.controller.MockOccupationsController;
import br.com.project.portfolio.mock.member.api.web.dto.OccupationDto;
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
@RequestMapping("/api/occupations")
public class MockOccupationsControllerImpl implements MockOccupationsController {

    @GetMapping("/{id}")
    public ResponseEntity<OccupationDto> getById(@PathVariable Integer id) {

        if (Objects.isNull(id))
            throw new NotFoundException("Occupation not found");

        return ResponseEntity.ok(
                Arrays.stream(Occupation.values())
                        .filter(c -> c.getId().equals(id))
                        .map(o -> new OccupationDto(o.getId(), o.getOccupation()))
                        .findFirst()
                        .orElseThrow(() -> new NotFoundException("Invalid occupation id: " + id))
        );
    }

    @GetMapping
    public ResponseEntity<List<OccupationDto>> list() {
        return ResponseEntity.ok(
                Arrays.stream(Occupation.values())
                        .map(o -> new OccupationDto(o.getId(), o.getOccupation()))
                        .toList()
        );
    }
}