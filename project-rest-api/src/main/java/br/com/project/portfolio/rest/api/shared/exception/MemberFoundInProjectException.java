package br.com.project.portfolio.rest.api.shared.exception;

public class MemberFoundInProjectException extends RuntimeException {

    public MemberFoundInProjectException(String s) {
        super(s);
    }
    public MemberFoundInProjectException(String s, Throwable throwable) {
        super(s, throwable);
    }

}
