package br.com.project.portfolio.rest.api.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransferObject<T> {

    private T content;
    private String message;
    private int status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PaginationInfo pagination;
    private LocalDateTime timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    public static <T> TransferObject<T> success(T data, HttpStatus status, String message, Page<?> page) {
        return TransferObject.<T>builder()
                .content(data)
                .message(message)
                .status(status.value())
                .pagination(
                        PaginationInfo.builder()
                                .page(page.getNumber())
                                .size(page.getSize())
                                .totalElements(page.getTotalElements())
                                .totalPages(page.getTotalPages())
                                .build()
                )
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> TransferObject<T> success(T data, HttpStatus status, String message) {
        return TransferObject.<T>builder()
                .content(data)
                .message(message)
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static TransferObject<Object> error(
            HttpStatus httpStatus,
            String message,
            Exception ex) {
        return TransferObject.builder()
                .message(message)
                .status(httpStatus.value())
                .timestamp(LocalDateTime.now())
                .error(ex.getClass().getSimpleName() + ": " + ex.getMessage())
                .build();
    }

}
