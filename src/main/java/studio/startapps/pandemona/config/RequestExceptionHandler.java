package studio.startapps.pandemona.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import studio.startapps.pandemona.exception.auth.InvalidAuthCredentialsException;
import studio.startapps.pandemona.exception.auth.TokenExpiredException;
import studio.startapps.pandemona.exception.auth.TokenSubjectMismatchException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger exceptionLogger = LoggerFactory.getLogger(RequestExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({InvalidAuthCredentialsException.class, TokenSubjectMismatchException.class, TokenExpiredException.class})
    protected void handleUnauthorized() {
        this.exceptionLogger.info("[RestExceptionHandler.handleUnauthorized]");
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> errors.add(String.format("%s %s", ((FieldError) error).getField(), error.getDefaultMessage())));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
