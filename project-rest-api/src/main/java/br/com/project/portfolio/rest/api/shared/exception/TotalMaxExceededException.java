package br.com.project.portfolio.rest.api.shared.exception;

public class TotalMaxExceededException extends RuntimeException {

    public TotalMaxExceededException(String s) {
        super(s);
    }
    public TotalMaxExceededException(String s, Throwable throwable) {
        super(s, throwable);
    }

}
