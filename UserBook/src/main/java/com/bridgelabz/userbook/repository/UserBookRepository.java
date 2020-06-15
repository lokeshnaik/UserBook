package com.bridgelabz.userbook.repository;
import java.util.List;
import java.util.Optional;
import com.bridgelabz.userbook.dto.UserLoginInformation;
import com.bridgelabz.userbook.dto.UserUpdatePassword;
import com.bridgelabz.userbook.entity.UserAddressBookEntity;
import com.bridgelabz.userbook.entity.UserBookEntity;
public interface UserBookRepository
{
  public void save(UserBookEntity information);
  Optional<UserBookEntity> getUser(String email);
  Optional<UserBookEntity> loginValidate(UserLoginInformation userlogininformation);
  public boolean verify(Long userid);
  public boolean updatePassword(UserUpdatePassword updatePassword, Long id);
  public Optional<UserBookEntity> getUserById(Long id) ;
  public boolean updateRandomPassword(String password, Long id);
  public void savebook(UserAddressBookEntity userbookentitty); 
  public Optional<UserAddressBookEntity> getUserBook(Long bookid,Long userid);
  public Optional<UserAddressBookEntity> getUserBookTitle(String title,Long userid);
  public List<UserAddressBookEntity>getAllBook(Long userid);
}
