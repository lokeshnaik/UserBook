package com.bridgelabz.userbook.exception;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.bridgelabz.userbook.response.ExceptionResponse;
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{

	@ExceptionHandler(CustomException.class)
	public final ResponseEntity<ExceptionResponse> userException(CustomException ex) {
		
		ExceptionResponse exp = new ExceptionResponse();
		exp.setMessage(ex.getMessage());
		exp.setCode(ex.getStatus());
		exp.setTime(LocalDateTime.now());
       return ResponseEntity.status(exp.getCode()).body(new ExceptionResponse(exp.getMessage(), exp.getCode(),exp.getTime()));

	}

}
