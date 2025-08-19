package br.com.project.portfolio.rest.api.domain.model;

import java.math.BigDecimal;

public interface BudgetByStatus {
    ProjectStatus getStatus();
    BigDecimal getTotal();
}

