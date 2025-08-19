package br.com.project.portfolio.rest.api.web.controller;

import br.com.project.portfolio.rest.api.domain.model.BudgetByStatus;
import br.com.project.portfolio.rest.api.domain.model.ProjectStatus;
import br.com.project.portfolio.rest.api.domain.service.MemberService;
import br.com.project.portfolio.rest.api.domain.service.ProjectService;
import br.com.project.portfolio.rest.api.web.controller.api.ReportControllerApi;
import br.com.project.portfolio.rest.api.web.dto.response.TransferObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static br.com.project.portfolio.rest.api.shared.util.Constants.GENERIC_SUCCESS_MESSAGE;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/projects/report")
@RequiredArgsConstructor
public class ReportController implements ReportControllerApi {

    private final ProjectService projectService;
    private final MemberService memberService;

    @Override
    @GetMapping("membros-unicos-alocados")
    public ResponseEntity<TransferObject<Long>> countUniqueMembersAllocated() {
        return ResponseEntity.status(HttpStatus.OK).body(
                TransferObject.success(
                        memberService.countUniqueMembersAllocated(),
                        HttpStatus.OK,
                        GENERIC_SUCCESS_MESSAGE
                ));
    }

    @Override
    @GetMapping("media-duracao-projetos-encerrados")
    public ResponseEntity<TransferObject<Double>> averageClosedProjectsDurationDays() {
        return ResponseEntity.status(HttpStatus.OK).body(
                TransferObject.success(
                        projectService.averageClosedProjectsDurationDays(),
                        HttpStatus.OK,
                        GENERIC_SUCCESS_MESSAGE
                ));
    }

    @Override
    @GetMapping("total-orcado-por-status")
    public ResponseEntity<TransferObject<List<BudgetByStatus>>> findTotalBudgetByStatus() {
        return ResponseEntity.status(HttpStatus.OK).body(
                TransferObject.success(
                        projectService.findTotalBudgetByStatus(),
                        HttpStatus.OK,
                        GENERIC_SUCCESS_MESSAGE
                ));
    }

    @Override
    @GetMapping("quantidade-projetos-por-status")
    public ResponseEntity<TransferObject<Long>> countByStatus(ProjectStatus status) {
        return ResponseEntity.status(HttpStatus.OK).body(
                TransferObject.success(
                        projectService.countByStatus(status) ,
                        HttpStatus.OK,
                        GENERIC_SUCCESS_MESSAGE
                ));
    }
}