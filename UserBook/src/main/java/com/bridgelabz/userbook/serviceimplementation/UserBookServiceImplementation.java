package com.bridgelabz.userbook.serviceimplementation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.userbook.dto.UserBookAddress;
import com.bridgelabz.userbook.dto.UserInformation;
import com.bridgelabz.userbook.dto.UserLoginInformation;
import com.bridgelabz.userbook.dto.UserUpdatePassword;
import com.bridgelabz.userbook.entity.UserAddressBookEntity;
import com.bridgelabz.userbook.entity.UserBookEntity;
import com.bridgelabz.userbook.exception.CustomException;
import com.bridgelabz.userbook.repository.UserBookRepositoryImplementation;
import com.bridgelabz.userbook.response.MailResponse;
import com.bridgelabz.userbook.response.MailingandResponseOperation;
import com.bridgelabz.userbook.service.UserBookService;
import com.bridgelabz.userbook.utility.JWTOperations;
import com.bridgelabz.userbook.utility.JavaMessageService;
@Service
public class UserBookServiceImplementation implements UserBookService
{
	@Autowired
	BCryptPasswordEncoder encryption;
	@Autowired
	UserBookRepositoryImplementation repository;
	@Autowired
	JWTOperations jwtop;
	@Autowired
	private MailResponse mail;
    @Autowired
	private JavaMessageService messageService;
    @Autowired
    MailingandResponseOperation response;
	
   
	@Override
	@Transactional
	public UserBookEntity userRegistration(UserInformation information) throws CustomException
	{
		UserBookEntity userbook=new UserBookEntity();
		if (repository.getUser(information.getEmail()).isPresent() == false)
		{
		BeanUtils.copyProperties(information, userbook);
		userbook.setCreatedDate(LocalDateTime.now());
		userbook.setVerified(false);
		String password=userbook.getPassword();
		String hidepassword=encryption.encode(password);
		userbook.setPassword(hidepassword);
		repository.save(userbook);
		String mailResponse = response.fromMessage("http://localhost:8880/user/verify",jwtop.JwtToken(userbook.getUserId()));
		mail.setEmail(userbook.getEmail());
		mail.setMessage(mailResponse);
		mail.setSubject("verification");
		messageService.sendEmail(userbook.getEmail(),"verification",mailResponse);
		return userbook;
		
	} else
	{
		throw new CustomException("Email id already exists", HttpStatus.FORBIDDEN);
	}
		
	}


	@Override
	@Transactional
	public String userLogin(UserLoginInformation userinfo) throws CustomException 
	{
		UserBookEntity userbook = repository.loginValidate(userinfo).orElseThrow(() -> new CustomException("Email not registered", HttpStatus.NOT_FOUND));
		UserBookEntity userentity=new UserBookEntity();
		Long userid=userentity.getUserId();
		 String token=jwtop.JwtToken(userid);
        if (encryption.matches(userinfo.getPassword(), userbook.getPassword())) 
        {
			return token;
		}
        else 
        {
			String mailResponse = response.fromMessage("http://localhost:8880/user/verify",jwtop.JwtToken(userbook.getUserId()));
			messageService.sendEmail(userinfo.getEmail(), "Verification", mailResponse);
			throw new CustomException("Password is not matched", HttpStatus.BAD_REQUEST);
		}
	}


	@Override
	@Transactional
	public boolean verify(String token) throws CustomException
	{
		Long userid=(long)jwtop.parseJWT(token);
		boolean value=repository.verify(userid);
		return value;
	}


	@Override
	@Transactional
	public boolean updatePassword(UserUpdatePassword updatePassword, String token) throws CustomException 
	{
		Long id = (long) 0;
		boolean updatePasswordFlag = false;

		id = (Long) jwtop.parseJWT(token);
		UserBookEntity userbookentity;
       userbookentity = repository.getUserById(id).orElseThrow(() -> new CustomException("User is not registered", HttpStatus.NOT_FOUND));

		if (encryption.matches(updatePassword.getOldPassword(), userbookentity.getPassword())) {
			String epassword = encryption.encode(updatePassword.getConfirmPassword());
			updatePassword.setConfirmPassword(epassword);
			updatePasswordFlag = repository.updatePassword(updatePassword, id);
		} else {
			throw new CustomException("Old password is invalid", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}

		return updatePasswordFlag;
	}

	@Override
	@Transactional
	public boolean forgotPassword(String email) throws CustomException 
	{
		boolean userExist = false;
		UserBookEntity userbookentity = repository.getUser(email).orElseThrow(() -> new CustomException("User not registered", HttpStatus.NOT_FOUND));
			if (userbookentity.isVerified() == true && userbookentity != null) {
				Random random = new Random();
             	String password = String.valueOf(random.nextInt());
            	String mailResponse = response.fromMessage("Your temporary password is", password);
				String encodedPassword = encryption.encode(password);
				repository.updateRandomPassword(encodedPassword, userbookentity.getUserId());
				mail.setEmail(userbookentity.getEmail());
				mail.setMessage(mailResponse);
				mail.setSubject("verification");
				messageService.sendEmail(userbookentity.getEmail(),"verification",mailResponse);
				userExist = true;
			}
	return userExist;
	}


	@Override
	@Transactional
	public UserBookEntity getUserByToken(String token) throws CustomException 
	{
		UserBookEntity userbookentity=new UserBookEntity();
		Long id = (long) 0;
		id = jwtop.parseJWT(token);
		userbookentity = repository.getUserById(id).orElseThrow(() -> new CustomException("User not present", HttpStatus.NOT_FOUND));
         return userbookentity;
	}


	@Override
	public UserBookEntity getUserById(Long userid) throws CustomException 
	{
		UserBookEntity userbookentity=new UserBookEntity();
		userbookentity = repository.getUserById(userid).orElseThrow(() -> new CustomException("User is not present", HttpStatus.NOT_FOUND));
        return userbookentity;
	}


	@Override
	public UserAddressBookEntity createUserBook(String token,UserBookAddress userbook) throws CustomException 
	{
		UserAddressBookEntity useraddressbookentity=new UserAddressBookEntity();
		Long userid=jwtop.parseJWT(token);
		Long bookid=useraddressbookentity.getBookid();
		if (repository.getUserBookTitle(userbook.getTitle(),userid).isPresent() == false)
		{
		BeanUtils.copyProperties(userbook, useraddressbookentity);
		useraddressbookentity.setPurchasedTime(LocalDateTime.now());
		useraddressbookentity.setUserId(userid);
		useraddressbookentity.setBookid(bookid);
		repository.savebook(useraddressbookentity);
		
		return useraddressbookentity;
		
	} else
	{
		throw new CustomException("Book Title is already exists", HttpStatus.FORBIDDEN);
	}
	}


	@Override
	@Transactional
	public UserAddressBookEntity getuserbyIdTitle(Long userid, Long bookid) throws CustomException
	{
		UserAddressBookEntity userbook=new UserAddressBookEntity();
		System.out.println("Hey Lokesh");
		userbook= repository.getUserBook(bookid, userid).orElseThrow(() -> new CustomException("Book or user id is not present", HttpStatus.NOT_FOUND));
		System.out.println("userbook "+userbook);
		return userbook;
	}


	@Override
	public List<UserAddressBookEntity> getAllBooks(String token) 
	{
		UserAddressBookEntity userbook=new UserAddressBookEntity();
		Long userId=jwtop.parseJWT(token);
		List<UserAddressBookEntity> list=repository.getAllBook(userId);
		return list;
	}
	
}
