package br.com.project.portfolio.rest.api.shared.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String s) {
        super(s);
    }
    public NotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

}
