package br.com.project.portfolio.rest.api.shared.exception;

public class NotPermittedException extends RuntimeException {

    public NotPermittedException(String s) {
        super(s);
    }
    public NotPermittedException(String s, Throwable throwable) {
        super(s, throwable);
    }

}
