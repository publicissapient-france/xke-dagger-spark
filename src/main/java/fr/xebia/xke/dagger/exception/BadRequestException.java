package fr.xebia.xke.dagger.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(Exception e) {
        super(e);
    }
}
