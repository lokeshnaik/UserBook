package com.bridgelabz.userbook.service;
import java.util.List;

import com.bridgelabz.userbook.dto.UserBookAddress;
import com.bridgelabz.userbook.dto.UserInformation;
import com.bridgelabz.userbook.dto.UserLoginInformation;
import com.bridgelabz.userbook.dto.UserUpdatePassword;
import com.bridgelabz.userbook.entity.UserAddressBookEntity;
import com.bridgelabz.userbook.entity.UserBookEntity;
import com.bridgelabz.userbook.exception.CustomException;
public interface UserBookService
{
  public UserBookEntity userRegistration(UserInformation information)throws CustomException;
  public String userLogin(UserLoginInformation userinfo) throws CustomException;
  public boolean verify(String token)throws CustomException;
  public boolean updatePassword(UserUpdatePassword updatePassword, String token) throws CustomException ;
  public boolean forgotPassword(String email) throws CustomException;
  public UserBookEntity getUserByToken(String token) throws CustomException ;
  public UserBookEntity getUserById(Long userid) throws CustomException ;
  public UserAddressBookEntity createUserBook(String token,UserBookAddress userbook) throws CustomException;
 public UserAddressBookEntity getuserbyIdTitle(Long userid,Long bookid) throws CustomException;
 public List<UserAddressBookEntity> getAllBooks(String token);
}
