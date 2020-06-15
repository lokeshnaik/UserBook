package com.bridgelabz.userbook.response;
import org.springframework.stereotype.Component;
import lombok.Data;
@Data
@Component
public class MailResponse 
{
	private String email;
	private String subject;
	private String message;
}
