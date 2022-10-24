package com.practise.luteat.exceptions;

public class UsernameEmailExistsException extends RuntimeException {

    public UsernameEmailExistsException(String message) {
        super(message);
    }
}
