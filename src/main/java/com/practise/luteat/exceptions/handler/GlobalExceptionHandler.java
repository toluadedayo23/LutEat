package com.practise.luteat.exceptions.handler;

import com.practise.luteat.exceptions.EmailVerificationException;
import com.practise.luteat.exceptions.ErrorResponse;
import com.practise.luteat.exceptions.RefreshTokenException;
import com.practise.luteat.exceptions.UsernameEmailExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailVerificationException.class)
    public ResponseEntity<ErrorResponse> handleEmailVerificationException(Exception e) {

        EmailVerificationException userEmailVerificationException = (EmailVerificationException) e;
        HttpStatus status = HttpStatus.NOT_FOUND;

        log.error("EmailVerification Exception Error");

        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);

    }

    @ExceptionHandler(UsernameEmailExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameEmailExistsException(Exception e) {

        UsernameEmailExistsException usernameEmailExistsException = (UsernameEmailExistsException) e;

        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;

        log.error("Username or Email already exists");

        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);

    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        log.error("Username not found in the UserDetailsService");

        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;

        log.error("Authentication failure");

        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        List<String> validationList = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());
        log.error("Validation error: " + validationList);
        return new ResponseEntity<>(new ErrorResponse(status, errorMessage), status);
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<ErrorResponse> handleRefreshTokenException(Exception e){
        RefreshTokenException refreshTokenException = (RefreshTokenException) e;
        HttpStatus status = HttpStatus.NOT_FOUND;
        log.error("Invalid Refresh Token");
        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        log.error("Internal Error");

        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }

}
