package br.com.project.portfolio.rest.api.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaginationInfo {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}
