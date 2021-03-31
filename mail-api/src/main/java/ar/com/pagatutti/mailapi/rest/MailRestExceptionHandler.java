package ar.com.pagatutti.mailapi.rest;

import javax.mail.MessagingException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import ar.com.pagatutti.apicommons.api.CustomRestExceptionHandler;
import ar.com.pagatutti.apicommons.beans.ApiError;

import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateNotFoundException;

@ControllerAdvice
public class MailRestExceptionHandler extends CustomRestExceptionHandler{

	 @ExceptionHandler({ TemplateNotFoundException.class })
	 public ResponseEntity<Object> handleTemplateNotFoundException(final TemplateNotFoundException ex, final WebRequest request) {
	   final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Template not found", ex.getMessage());
	   return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	 }
	 
	 @ExceptionHandler({  MalformedTemplateNameException.class })
	 public ResponseEntity<Object> handleMalformedTemplateNameException(final  MalformedTemplateNameException ex, final WebRequest request) {
	   final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "MalformedTemplateNameException", ex.getMessage());
	   return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	 }
	 
	 @ExceptionHandler({MessagingException.class })
	 public ResponseEntity<Object> handleMessagingException(final  MessagingException ex, final WebRequest request) {
	   final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error al enviar el mail", ex.getMessage());
	   return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	 }
	 
	
}
