package com.practise.luteat.exceptions.handler;

import com.practise.luteat.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailVerificationException.class)
    public ErrorResponse handleEmailVerificationException(Exception e) {

        EmailVerificationException userEmailVerificationException = (EmailVerificationException) e;
        HttpStatus status = HttpStatus.NOT_FOUND;

        log.error("EmailVerification Exception Error");

        return new ErrorResponse(status, userEmailVerificationException.getMessage());

    }

    @ExceptionHandler(UsernameEmailExistsException.class)
    public ErrorResponse handleUsernameEmailExistsException(Exception e) {

        UsernameEmailExistsException usernameEmailExistsException = (UsernameEmailExistsException) e;

        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;

        log.error("Username or Email already exists");

        return new ErrorResponse(status, usernameEmailExistsException.getMessage());

    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorResponse handleUsernameNotFoundException(UsernameNotFoundException e) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        log.error("Username not found in the UserDetailsService");

        return new ErrorResponse(status, e.getMessage());
    }


    @ExceptionHandler(AuthenticationException.class)
    public ErrorResponse handleAuthenticationException(AuthenticationException e) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;

        log.error("Authentication failure, user not authenticated");

        return new ErrorResponse(status, e.getMessage());
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        List<String> validationList = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());
        log.error("Validation error: " + validationList);
        return new ResponseEntity<>(new ErrorResponse(status, errorMessage), status);
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ErrorResponse handleRefreshTokenException(Exception e){
        RefreshTokenException refreshTokenException = (RefreshTokenException) e;
        HttpStatus status = HttpStatus.NOT_FOUND;
        log.error("Invalid Refresh Token");
        return new ErrorResponse(status, refreshTokenException.getMessage());
    }

    @ExceptionHandler(LockedException.class)
    public ErrorResponse handleLockedException(LockedException e){
        HttpStatus status = HttpStatus.LOCKED;
        log.error("User account has been locked for 24 hours");
        return new ErrorResponse(status, e.getMessage());
    }

    @ExceptionHandler(MenuOrderException.class)
    public ErrorResponse handleMenuOrderException(Exception e){
        MenuOrderException menuOrderException = (MenuOrderException) e;
        HttpStatus status = HttpStatus.NOT_FOUND;
        log.error("Menu Not Found");
        return new ErrorResponse(status, menuOrderException.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException e){
        HttpStatus status = HttpStatus.FORBIDDEN;
        log.error("User does not have the proper permission to access this resource");
        return new ErrorResponse(status, e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        log.error("Internal Error");

        return new ErrorResponse(status, e.getMessage());
    }

}
