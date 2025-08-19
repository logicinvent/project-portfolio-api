package br.com.project.portfolio.mock.member.api.shared.enums;

import lombok.Getter;

@Getter
public enum EmploymentContract {

    FUNCIONARIO(1, "Funcionário"),
    TERCEIRO(2, "Terceiro"),
    ESTAGIARIO(3, "Estagiário"),
    TEMPORARIO(4, "Temporário"),
    AUTONOMO(5, "Autônomo"),
    FREELANCER(6, "Freelancer"),
    CONSULTOR(7, "Consultor"),
    SOCIO(8, "Sócio"),
    VOLUNTARIO(9, "Voluntário");

    private final Integer id;
    private final String contract;

    EmploymentContract(Integer id, String contract) {
        this.id = id;
        this.contract = contract;
    }
}
