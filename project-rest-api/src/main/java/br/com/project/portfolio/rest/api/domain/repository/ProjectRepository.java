package br.com.project.portfolio.rest.api.domain.repository;

import br.com.project.portfolio.rest.api.domain.model.BudgetByStatus;
import br.com.project.portfolio.rest.api.domain.model.Project;
import br.com.project.portfolio.rest.api.domain.model.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    Page<Project> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("""
           select coalesce(sum(p.totalBudget), 0)
           from Project p
           where p.status = :status
           """)
    BigDecimal sumTotalBudgetByStatus(ProjectStatus status);

    @Query(value = """
        select coalesce(avg((p.dt_end_real - p.dt_start))::float, 0)
        from tb_projects p
        where p.ds_status = 'ENCERRADO'
          and p.dt_end_real is not null
        """, nativeQuery = true)
    Double averageClosedProjectsDurationDays();

    @Query("""
           select p.status as status,
                  coalesce(sum(p.totalBudget), 0) as total
           from Project p
           group by p.status
           """)
    List<BudgetByStatus> findTotalBudgetByStatus();

    Long countByStatus(ProjectStatus status);
}
