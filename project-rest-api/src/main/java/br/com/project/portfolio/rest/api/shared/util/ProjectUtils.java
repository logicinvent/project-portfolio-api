package br.com.project.portfolio.rest.api.shared.util;

import br.com.project.portfolio.rest.api.domain.model.Project;
import br.com.project.portfolio.rest.api.domain.model.ProjectRiskLevel;
import br.com.project.portfolio.rest.api.domain.model.ProjectStatus;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/**
 * Utility component for project-related business rules and calculations.
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Expose the set of statuses that prevent deletion of a project.</li>
 *   <li>Classify a project's risk level based on budget and planned duration.</li>
 *   <li>Provide helper logic for month-span calculations (ceiling).</li>
 * </ul>
 *
 * <p><b>Risk classification rules:</b></p>
 * <ul>
 *   <li><b>LOW</b> ({@link ProjectRiskLevel#BAIXO_RISCO}): budget ≤ R$ 100.000 and duration ≤ 3 months</li>
 *   <li><b>MEDIUM</b> ({@link ProjectRiskLevel#MEDIO_RISCO}): otherwise (when not LOW or HIGH)</li>
 *   <li><b>HIGH</b> ({@link ProjectRiskLevel#ALTO_RISCO}): budget &gt; R$ 500.000 or duration &gt; 6 months</li>
 * </ul>
 */
@Component
public class ProjectUtils {

    /** Threshold for low/medium boundary (R$ 100.000). */
    private static final BigDecimal HUNDRED_K = new BigDecimal("100000");

    /** Threshold for medium/high boundary (R$ 500.000). */
    private static final BigDecimal FIVE_HUNDRED_K = new BigDecimal("500000");

    /**
     * Immutable set of statuses that disallow deletion:
     * {@code INICIADO}, {@code EM_ANDAMENTO}, {@code ENCERRADO}.
     */
    private static final Set<ProjectStatus> NON_DELETABLE_STATUSES = Collections.unmodifiableSet(
            EnumSet.of(ProjectStatus.INICIADO, ProjectStatus.EM_ANDAMENTO, ProjectStatus.ENCERRADO)
    );

    /**
     * Returns the immutable set of statuses where deletion is not permitted.
     *
     * @return unmodifiable set of {@link ProjectStatus} that block deletion
     */
    public Set<ProjectStatus> nonDeletableStatuses() {
        return NON_DELETABLE_STATUSES;
    }

    /**
     * Classifies the risk level for the given {@link Project} using budget and planned duration.
     *
     * <p>Rules:</p>
     * <ul>
     *   <li>HIGH if {@code totalBudget &gt; 500.000} OR {@code monthsBetweenCeil(start, plannedEnd) &gt; 6}</li>
     *   <li>LOW  if {@code totalBudget ≤ 100.000} AND {@code monthsBetweenCeil(start, plannedEnd) ≤ 3}</li>
     *   <li>MEDIUM otherwise</li>
     * </ul>
     *
     * <p>Intended for MapStruct via {@link Named @Named("classifyRisk")}</p>
     *
     * @param project the project to classify (must have {@code totalBudget}, {@code startDate}, {@code plannedEndDate})
     * @return the computed {@link ProjectRiskLevel}
     * @throws NullPointerException if the project or required fields are null
     */
    @Named("classifyRisk")
    public ProjectRiskLevel classifyRisk(Project project) {
        Objects.requireNonNull(project, "project must not be null");
        BigDecimal budget = Objects.requireNonNull(project.getTotalBudget(), "totalBudget must not be null");
        LocalDate start   = Objects.requireNonNull(project.getStartDate(), "startDate must not be null");
        LocalDate end     = Objects.requireNonNull(project.getPlannedEndDate(), "plannedEndDate must not be null");

        long months = monthsBetweenCeil(start, end);

        if (budget.compareTo(FIVE_HUNDRED_K) > 0 || months > 6) {
            return ProjectRiskLevel.ALTO_RISCO;
        }
        if (budget.compareTo(HUNDRED_K) <= 0 && months <= 3) {
            return ProjectRiskLevel.BAIXO_RISCO;
        }
        return ProjectRiskLevel.MEDIO_RISCO;
    }

    /**
     * Calculates the number of months between {@code start} and {@code end}, rounding up (ceiling).
     * <p>Example: if the difference is 2 months and a few days, returns {@code 3}.</p>
     *
     * @param start start date (inclusive)
     * @param end   end date (exclusive for full months calculation; any remainder rounds up)
     * @return number of months rounded up to the next whole month
     */
    private long monthsBetweenCeil(LocalDate start, LocalDate end) {
        long m = ChronoUnit.MONTHS.between(start, end);
        LocalDate anchor = start.plusMonths(m);
        return anchor.isBefore(end) ? m + 1 : m;
    }
}