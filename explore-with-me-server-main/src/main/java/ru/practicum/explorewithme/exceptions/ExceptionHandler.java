package ru.practicum.explorewithme.exceptions;

import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public EwmException handleEntityNotFoundException(final EntityNotFoundException e) {
        String errorMessage = e.getMessage();

        return new EwmException(
                errorMessage,
                LocalDateTime.now()
        );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public EwmException handleIllegalArgumentException(final IllegalArgumentException e) {
        return new EwmException(
                e.getMessage(),
                LocalDateTime.now()
        );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public EwmException handleEmptyResultException(final EmptyResultDataAccessException e) {
        return new EwmException(
                e.getMessage(),
                LocalDateTime.now()
        );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public EwmException handleSqlException(final PSQLException e) {
        return new EwmException(
                e.getMessage(),
                LocalDateTime.now()
        );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public EwmException handleIntegrityException(final DataIntegrityViolationException e) {
        return new EwmException(
                e.getMessage(),
                LocalDateTime.now()
        );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    public EwmException handleAccessException(final AccessException e) {
        return new EwmException(
                e.getMessage(),
                LocalDateTime.now()
        );
    }
}
