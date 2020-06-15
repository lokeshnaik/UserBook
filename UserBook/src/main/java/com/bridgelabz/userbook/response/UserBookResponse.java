package com.bridgelabz.userbook.response;
import lombok.Data;
@Data
public class UserBookResponse 
{
public UserBookResponse() 
{
		
}
	String message;
	int statusCode;
	Object data;
	public UserBookResponse(String message, int statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
	public UserBookResponse(String message, int statusCode, Object data) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.data = data;
	}
}
