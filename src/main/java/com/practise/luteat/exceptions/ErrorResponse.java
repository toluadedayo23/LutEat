package com.practise.luteat.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
public class ErrorResponse {

    private Date timestamp;
    private int code;
    private String status;
    private String message;

    public ErrorResponse(){
        this.timestamp = new Date();

    }

    public ErrorResponse(HttpStatus httpStatus, String message){
        this.timestamp = new Date();
        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
    }
}
