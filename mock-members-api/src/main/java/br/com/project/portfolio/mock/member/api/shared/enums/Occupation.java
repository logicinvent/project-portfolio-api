package br.com.project.portfolio.mock.member.api.shared.enums;

public enum Occupation {

    PRESIDENTE(1, "Presidente"),
    DIRETOR_EXECUTIVO(2, "Diretor Executivo"),
    GERENTE_PROJETOS(3, "Gerente de Projetos"),
    GERENTE_TI(4, "Gerente de TI"),
    COORDENADOR_EQUIPE(5, "Coordenador de Equipe"),

    ARQUITETO_SOFTWARE(6, "Arquiteto de Software"),
    ARQUITETO_SOLUCOES(7, "Arquiteto de Soluções"),
    ARQUITETO_DADOS(8, "Arquiteto de Dados"),
    ARQUITETO_INFRA(9, "Arquiteto de Infraestrutura"),
    ARQUITETO_CLOUD(10, "Arquiteto de Cloud"),

    DESENVOLVEDOR_BACKEND(11, "Desenvolvedor Backend"),
    DESENVOLVEDOR_FRONTEND(12, "Desenvolvedor Frontend"),
    DESENVOLVEDOR_FULLSTACK(13, "Desenvolvedor Fullstack"),
    DESENVOLVEDOR_MOBILE(14, "Desenvolvedor Mobile"),
    ENGENHEIRO_SOFTWARE(15, "Engenheiro de Software"),
    ENGENHEIRO_DEVOPS(16, "Engenheiro DevOps"),
    ENGENHEIRO_DADOS(17, "Engenheiro de Dados"),

    ANALISTA_SISTEMAS(18, "Analista de Sistemas"),
    ANALISTA_NEGOCIOS(19, "Analista de Negócios"),
    ANALISTA_TESTES(20, "Analista de Testes"),
    QA_ENGINEER(21, "QA Engineer"),
    CIENTISTA_DADOS(22, "Cientista de Dados"),

    SUPORTE_TECNICO(23, "Suporte Técnico"),
    DBA(24, "Administrador de Banco de Dados"),
    ADMINISTRADOR_REDE(25, "Administrador de Redes"),
    ANALISTA_INFRA(26, "Analista de Infraestrutura"),

    SCRUM_MASTER(27, "Scrum Master"),
    PRODUCT_OWNER(28, "Product Owner"),
    UX_DESIGNER(29, "UX Designer"),
    UI_DESIGNER(30, "UI Designer");

    private final Integer id;
    private final String occupation;

    Occupation(Integer id, String occupation) {
        this.id = id;
        this.occupation = occupation;
    }

    public Integer getId() {
        return id;
    }

    public String getOccupation() {
        return occupation;
    }
}
