package br.com.project.portfolio.rest.api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_MEMBERS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Member {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "ID_MEMBER", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "ID_EXTERNAL", nullable = false)
    private Integer externalId;

    @Column(name = "NM_MEMBER", nullable = false, length = 255)
    @NotBlank @Size(max = 255)
    private String name;

    @CreationTimestamp
    @Column(name = "DT_CREATED", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "DT_UPDATED", nullable = false)
    private OffsetDateTime updatedAt;
}
