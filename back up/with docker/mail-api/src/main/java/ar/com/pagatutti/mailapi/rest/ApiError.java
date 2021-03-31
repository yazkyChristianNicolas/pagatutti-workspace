package ar.com.pagatutti.mailapi.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiError {
	 
    private HttpStatus status;
    private String message;
    private List<String> errors;
    private int code;
 
    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.code = status.value();
    }
    
    public ApiError(HttpStatus status, String message, List<String> errors, Double code) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.code = status.value();
    }
 
    public ApiError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        this.code = status.value();
        errors = Arrays.asList(error);
    }
    
    public ApiError(HttpStatus status, String message, String error, Double code) {
        super();
        this.status = status;
        this.message = message;
        this.code = status.value();
        errors = Arrays.asList(error);
    }
}
