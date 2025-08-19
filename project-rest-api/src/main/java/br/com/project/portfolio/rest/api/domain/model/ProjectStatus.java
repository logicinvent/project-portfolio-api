package br.com.project.portfolio.rest.api.domain.model;

import java.util.List;

public enum ProjectStatus {
    EM_ANALISE,
    ANALISE_REALIZADA,
    ANALISE_APROVADA,
    INICIADO,
    PLANEJADO,
    EM_ANDAMENTO,
    ENCERRADO,
    CANCELADO;

    private static final List<ProjectStatus> FLOW = List.of(
            EM_ANALISE,
            ANALISE_REALIZADA,
            ANALISE_APROVADA,
            INICIADO,
            PLANEJADO,
            EM_ANDAMENTO,
            ENCERRADO
    );

    public static boolean canTransition(ProjectStatus current, ProjectStatus target) {
        if (current == null || target == null) return false;
        if (current.equals(CANCELADO)) return true;
        int ci = FLOW.indexOf(current);
        return ci >= 0 && ci + 1 < FLOW.size() && FLOW.get(ci + 1) == target;
    }

}