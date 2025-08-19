package br.com.project.portfolio.rest.api.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProjectMemberId implements Serializable {

    @JdbcTypeCode(SqlTypes.UUID)
    private UUID projectId;

    @JdbcTypeCode(SqlTypes.UUID)
    private UUID memberId;
}

