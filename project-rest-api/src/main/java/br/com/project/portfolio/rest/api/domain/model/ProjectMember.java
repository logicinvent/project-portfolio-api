package br.com.project.portfolio.rest.api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "TB_PROJECT_MEMBERS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProjectMember {

    @EmbeddedId
    private ProjectMemberId id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("projectId")
    @JoinColumn(
            name = "ID_PROJECT",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_PROJECT_MEMBER_PROJECT")
    )
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("memberId")
    @JoinColumn(
            name = "ID_MEMBER",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_PROJECT_MEMBER_MEMBER")
    )
    private Member member;

    @Column(name = "DT_ALLOCATED", nullable = false)
    @NotNull
    private LocalDate allocatedDate;

    @CreationTimestamp
    @Column(name = "DT_CREATED", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "DT_UPDATED", nullable = false)
    private OffsetDateTime updatedAt;
}
