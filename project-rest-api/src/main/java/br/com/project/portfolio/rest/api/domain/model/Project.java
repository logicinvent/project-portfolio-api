package br.com.project.portfolio.rest.api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_PROJECTS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Project {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "ID_PROJECT", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "NM_PROJECT", nullable = false, length = 255)
    @NotBlank
    @Size(max = 255)
    private String name;

    @Column(name = "DT_START", nullable = false)
    @NotNull
    private LocalDate startDate;

    @Column(name = "DT_END_PLANNED", nullable = false)
    @NotNull
    private LocalDate plannedEndDate;

    @Column(name = "DT_END_REAL")
    private LocalDate realEndDate;

    @Column(name = "VL_TOTAL_BUDGET", nullable = false, precision = 15, scale = 2)
    @NotNull
    @PositiveOrZero
    private BigDecimal totalBudget;

    @Column(name = "DS_DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(
            name = "ID_MANAGER",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_PROJECT_MANAGER")
    )
    private Member manager;

    @Enumerated(EnumType.STRING)
    @Column(name = "DS_STATUS", nullable = false)
    @NotNull
    private ProjectStatus status;

    @CreationTimestamp
    @Column(name = "DT_CREATED", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "DT_UPDATED", nullable = false)
    private OffsetDateTime updatedAt;

}
