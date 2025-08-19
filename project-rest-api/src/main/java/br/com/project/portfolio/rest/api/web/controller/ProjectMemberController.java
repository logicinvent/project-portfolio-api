package br.com.project.portfolio.rest.api.web.controller;

import br.com.project.portfolio.rest.api.domain.service.ProjectMemberService;
import br.com.project.portfolio.rest.api.web.controller.api.ProjectMemberControllerApi;
import br.com.project.portfolio.rest.api.web.dto.request.ProjectMemberRequest;
import br.com.project.portfolio.rest.api.web.dto.response.ProjectMemberResponse;
import br.com.project.portfolio.rest.api.web.dto.response.TransferObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static br.com.project.portfolio.rest.api.shared.util.Constants.GENERIC_SUCCESS_MESSAGE;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/project-members")
@RequiredArgsConstructor
public class ProjectMemberController implements ProjectMemberControllerApi {

    private final ProjectMemberService service;

    @PostMapping
    @Override
    public ResponseEntity<TransferObject<ProjectMemberResponse>> save(@Valid @RequestBody ProjectMemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                TransferObject.success(
                        service.save(request),
                        HttpStatus.CREATED,
                        GENERIC_SUCCESS_MESSAGE
                )
        );
    }

    @PutMapping("/projects/{projectId}/members/{memberId}")
    @Override
    public ResponseEntity<TransferObject<ProjectMemberResponse>> update(@PathVariable UUID projectId,
                                                                        @PathVariable Integer memberId,
                                                                        @Valid @RequestBody ProjectMemberRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                TransferObject.success(
                        service.update(projectId, memberId, request),
                        HttpStatus.OK,
                        GENERIC_SUCCESS_MESSAGE
                )
        );
    }

    @DeleteMapping("/projects/{projectId}/members/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable UUID projectId, @PathVariable Integer memberId) {
        service.delete(projectId, memberId);
    }

    @GetMapping("/projects/{projectId}/members/{memberId}")
    @Override
    public ResponseEntity<TransferObject<ProjectMemberResponse>> findById(@PathVariable UUID projectId,
                                                                          @PathVariable Integer memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                TransferObject.success(
                        service.findById(projectId, memberId),
                        HttpStatus.OK,
                        GENERIC_SUCCESS_MESSAGE
                )
        );
    }

    @GetMapping("/projects/{projectId}")
    @Override
    public ResponseEntity<TransferObject<List<ProjectMemberResponse>>> getByProject(@PathVariable UUID projectId,
                                                                                    @RequestParam(required = false) String name,
                                                                                    @ParameterObject Pageable pageable) {
        Page<ProjectMemberResponse> page = service.getByProject(projectId, name, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                TransferObject.success(
                        page.getContent(),
                        HttpStatus.OK,
                        GENERIC_SUCCESS_MESSAGE,
                        page
                )
        );
    }
}
