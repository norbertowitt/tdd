package br.com.abc.tdd.exceptionhandler;

import br.com.abc.tdd.exceptionhandler.exception.ApiException;
import br.com.abc.tdd.exceptionhandler.exception.UserNotFoundException;
import br.com.abc.tdd.exceptionhandler.exception.UserValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String MSG_GENERICA = "Ocorreu um erro ao processar a sua requisição.";
    private static final String MSG_ERRO_SINTAXE_REQUISICAO = "Há um erro de sintaxe na requisição.";

    @ExceptionHandler(value = {UserValidationException.class})
    public ResponseEntity<Object> handleUserValidationException(Exception e, HttpServletRequest httpServletRequest) {
        return apiExceptionBuilder(e, httpServletRequest.getRequestURL().toString(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(Exception e, HttpServletRequest httpServletRequest) {
        return apiExceptionBuilder(e, httpServletRequest.getRequestURL().toString(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleNotSpecifiedException(HttpServletRequest httpServletRequest) {
        return apiExceptionBuilder(MSG_GENERICA, httpServletRequest.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders httpHeaders, HttpStatus httpStatus,
                                                                  WebRequest webRequest) {
        return apiExceptionBuilder(MSG_ERRO_SINTAXE_REQUISICAO,
                ((ServletWebRequest) webRequest).getRequest().getRequestURL().toString(), httpStatus);
    }

    private ResponseEntity<Object> apiExceptionBuilder(Exception e, String path, HttpStatus httpStatus) {
        ApiException apiException = ApiException.builder()
                .message(e.getMessage())
                .path(path)
                .httpStatusCode(httpStatus.value())
                .httpStatus(httpStatus)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();

        return new ResponseEntity<>(apiException, httpStatus);
    }

    private ResponseEntity<Object> apiExceptionBuilder(String mensagemGenerica, String path, HttpStatus httpStatus) {
        ApiException apiException = ApiException.builder()
                .message(mensagemGenerica)
                .path(path)
                .httpStatusCode(httpStatus.value())
                .httpStatus(httpStatus)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .build();

        return new ResponseEntity<>(apiException, httpStatus);
    }
}
