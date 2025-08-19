package br.com.project.portfolio.rest.api.shared.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(String s) {
        super(s);
    }
    public MemberNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

}
