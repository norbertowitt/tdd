package br.com.abc.tdd.exceptionhandler.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Builder
public class ApiException {

    private final String message;
    private final String path;
    private final Integer httpStatusCode;
    private final HttpStatus httpStatus;
    private final ZonedDateTime zonedDateTime;

    public ApiException(String message, String path, Integer httpStatusCode, HttpStatus httpStatus,
                        ZonedDateTime zonedDateTime) {
        this.message = message;
        this.path = path;
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.zonedDateTime = zonedDateTime;
    }
}
