package br.com.project.portfolio.rest.api.shared.exception;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(String s) {
        super(s);
    }
    public ProjectNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

}
