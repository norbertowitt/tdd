package br.com.abc.tdd.exceptionhandler.exception;

public class LoginDataValidationException extends RuntimeException {

    public LoginDataValidationException(String message) {
        super(message);
    }

    public LoginDataValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
