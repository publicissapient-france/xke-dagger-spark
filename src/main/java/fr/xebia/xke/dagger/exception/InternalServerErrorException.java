package fr.xebia.xke.dagger.exception;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String error) {
        super(error);
    }

    public InternalServerErrorException(Exception e) {
        super(e);
    }
}
