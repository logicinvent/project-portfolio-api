package br.com.project.portfolio.rest.api.web.controller;

import br.com.project.portfolio.rest.api.domain.service.ProjectService;
import br.com.project.portfolio.rest.api.web.controller.api.ProjectControllerApi;
import br.com.project.portfolio.rest.api.web.dto.request.ProjectRequest;
import br.com.project.portfolio.rest.api.web.dto.response.ProjectResponse;
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
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController implements ProjectControllerApi {

    private final ProjectService service;

    @PostMapping
    @Override
    public ResponseEntity<TransferObject<ProjectResponse>> save(@Valid @RequestBody ProjectRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                TransferObject.success(
                        service.save(request),
                        HttpStatus.CREATED,
                        GENERIC_SUCCESS_MESSAGE
                )
        );
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<TransferObject<ProjectResponse>> update(@PathVariable UUID id,
                                                                  @Valid @RequestBody ProjectRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                TransferObject.success(
                        service.update(id, request),
                        HttpStatus.OK,
                        GENERIC_SUCCESS_MESSAGE
                )
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<TransferObject<ProjectResponse>> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                TransferObject.success(
                        service.findById(id),
                        HttpStatus.OK,
                        GENERIC_SUCCESS_MESSAGE
                )
        );
    }

    @GetMapping
    @Override
    public ResponseEntity<TransferObject<List<ProjectResponse>>> getList(
            @RequestParam(required = false) String name,
            @ParameterObject Pageable pageable) {

        Page<ProjectResponse> page = service.getList(name, pageable);

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
