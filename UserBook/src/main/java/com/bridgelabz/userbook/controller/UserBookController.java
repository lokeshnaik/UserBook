package com.bridgelabz.userbook.controller;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.userbook.dto.UserBookAddress;
import com.bridgelabz.userbook.dto.UserInformation;
import com.bridgelabz.userbook.dto.UserLoginInformation;
import com.bridgelabz.userbook.dto.UserUpdatePassword;
import com.bridgelabz.userbook.entity.UserAddressBookEntity;
import com.bridgelabz.userbook.entity.UserBookEntity;
import com.bridgelabz.userbook.exception.CustomException;
import com.bridgelabz.userbook.response.UserBookResponse;
import com.bridgelabz.userbook.serviceimplementation.UserBookServiceImplementation;
@RestController
@RequestMapping("/user")
public class UserBookController 
{
	@Autowired
	UserBookServiceImplementation service;
   @PostMapping("/register")
   public ResponseEntity<UserBookResponse> userRegister(@Valid@RequestBody UserInformation userinformation,BindingResult results) throws CustomException
   {
	   if(results.hasErrors())
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new UserBookResponse(results.getAllErrors().get(0).getDefaultMessage(), 200,"null"));
	   UserBookEntity userbook=service.userRegistration(userinformation);
	   return ResponseEntity.status(HttpStatus.CREATED).body(new UserBookResponse("Person is successful registered",200,userbook));
   } 
   @PostMapping("/login")
   public ResponseEntity<UserBookResponse> userLogin(@RequestBody UserLoginInformation userinformation) throws CustomException
   {
	   String userbook=service.userLogin(userinformation);
	return ResponseEntity.status(HttpStatus.CREATED).body(new UserBookResponse("User is Login",200,userbook));
	   
   }
   @GetMapping("/verify/{token}")
   public ResponseEntity<UserBookResponse> verify(@PathVariable String token)throws CustomException
   {
	   System.out.println("*************IN VERIFY MAIL******************");
	   boolean update = service.verify(token);
		if (update) 
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserBookResponse(token, 200, "VERIFIED"));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserBookResponse(token, 401, "Not VERIFIED"));
   }
   @PutMapping("/updatepassword/{token}")
   public ResponseEntity<UserBookResponse> updatePassword(@RequestBody UserUpdatePassword updatePassword,@RequestHeader String token)throws CustomException
   {
	   boolean value=service.updatePassword( updatePassword,token);
	   return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserBookResponse("USer password is updated",200,token));
	   
   }
   @PostMapping("/forgotpassword")
   public ResponseEntity<UserBookResponse> forgotPassword(@PathVariable String email) throws CustomException
   {
	   boolean value=service.forgotPassword(email);
	   return ResponseEntity.status(HttpStatus.CREATED).body(new UserBookResponse("User got the password",200,value));
   }
   @GetMapping("/getuserbytoken/{token}")
   public ResponseEntity<UserBookResponse> getUserByToken(@RequestHeader String token) throws CustomException
   {
	   UserBookEntity userbook=service.getUserByToken(token);
	   return ResponseEntity.status(HttpStatus.CREATED).body(new UserBookResponse("Got the userId",200,userbook));
   }
   @GetMapping("/getuserbyid/{userId}")
	public ResponseEntity<UserBookResponse> getuserbyId(@PathVariable("userId") Long userId) throws CustomException
	{
		UserBookEntity user=service.getUserById(userId);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserBookResponse("Welcome to user", 200,user));
	}
   @PostMapping("/createBook/{title}/{token}")
   public ResponseEntity<UserBookResponse>createUserBook(@RequestHeader String token,@RequestBody UserBookAddress userbook) throws CustomException
   {
	   UserAddressBookEntity user=service.createUserBook(token,userbook);
	   return ResponseEntity.status(HttpStatus.CREATED).body(new UserBookResponse("Book created",200,user));
   }
   @GetMapping("/getuserbyIdBookId/{userId}/{bookid}")
  	public ResponseEntity<UserBookResponse> getuserbyIdTitle(@PathVariable("userId") Long userId,@PathVariable Long bookid) throws CustomException
  	{
	   UserAddressBookEntity user=service.getuserbyIdTitle(userId,bookid);
	   return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserBookResponse("Welcome to user", 200,user));  

  	}
   @GetMapping("/getAllBooks/{token}")
 	public ResponseEntity<UserBookResponse> getAllBooks(@RequestHeader String token) throws CustomException
 	{
	  List<UserAddressBookEntity> user=service.getAllBooks(token);
	   return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserBookResponse("Welcome to user", 200,user));  

 	}
   
     
}
