package com.bridgelabz.userbook.dto;
import javax.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class UserBookAddress 
{
	   @NotBlank(message="Book no is mandatory")
	    private String title;
}
