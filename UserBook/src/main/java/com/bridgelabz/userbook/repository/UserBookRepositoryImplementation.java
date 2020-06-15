package com.bridgelabz.userbook.repository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.bridgelabz.userbook.dto.UserInformation;
import com.bridgelabz.userbook.dto.UserLoginInformation;
import com.bridgelabz.userbook.dto.UserUpdatePassword;
import com.bridgelabz.userbook.entity.UserAddressBookEntity;
import com.bridgelabz.userbook.entity.UserBookEntity;
@Repository
public class UserBookRepositoryImplementation implements UserBookRepository
{
  @PersistenceContext
  private EntityManager entityManager;
  @Override
  @Transactional
	public void save(UserBookEntity information) 
	{
		Session session=entityManager.unwrap(Session.class);
		 ((Session)session).saveOrUpdate(information);	
		
	}
	
	@Override
	@Transactional
	public Optional<UserBookEntity> getUser(String email) {

		Session session = entityManager.unwrap(Session.class);
		return session.createQuery("FROM UserBookEntity where email =:email").setParameter("email", email).uniqueResultOptional();

	}

	@Override
	public Optional<UserBookEntity> loginValidate(UserLoginInformation userlogininformation) 
	{
		String email=userlogininformation.getEmail();
		Session session=entityManager.unwrap(Session.class);
		return session.createQuery("FROM UserBookEntity where email =:email").setParameter("email", email).uniqueResultOptional();
	}

	@Override
	public boolean verify(Long userid) 
	{
		Session session=entityManager.unwrap(Session.class);
		TypedQuery<UserBookEntity> query=session.createQuery("update UserBookEntity set is_verified =:verify where user_id=:userid");
		query.setParameter("verify", true);
		query.setParameter("userid", userid);
		int result=query.executeUpdate();
		if(result>0)
		{
			return true;
		}
		else
		{
			return false;	
		}
	}
	@Override
	public boolean updatePassword(UserUpdatePassword updatePassword, Long id) {

		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("update UserBookEntity set password =:p" + " " + " " + "where user_id=:i");
         q.setParameter("p", updatePassword.getNewPassword());
		q.setParameter("i", id);
		// q.setParameter("pass", updatePassword.getOldPassword());
		int status = q.executeUpdate();
		if (status > 0) {
			return true;

		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<UserBookEntity> getUserById(Long id) 
	{
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery("FROM UserBookEntity where user_id=:id").setParameter("id", id).uniqueResultOptional();
	}
	@Override
	public boolean updateRandomPassword(String password, Long id) {
		Session session = entityManager.unwrap(Session.class);
      Query q = session.createQuery("update UserBookEntity set password =:p" + " " + " " + "where user_id=:id");
		q.setParameter("p", password);
		q.setParameter("i", id);
		int status = q.executeUpdate();
		if (status > 0) {
			return true;

		} else {
			return false;
		}

	}

	@Override
	public void savebook(UserAddressBookEntity userbookentity) 
	{
		Session session=entityManager.unwrap(Session.class);
		 ((Session)session).saveOrUpdate(userbookentity);		
	}
	
	@SuppressWarnings("unchecked")
	@Override	
	public Optional<UserAddressBookEntity> getUserBook(Long bookid,Long userid) 
	{

		Session session = entityManager.unwrap(Session.class);
		return  session.createQuery("From  UserAddressBookEntity where user_id=:userid and bookid=:bookid ").setParameter("userid",userid).setParameter("bookid", bookid).uniqueResultOptional();
      
	}
	@SuppressWarnings("unchecked")
	@Override	
	public Optional<UserAddressBookEntity> getUserBookTitle(String title,Long userid) 
	{

		Session session = entityManager.unwrap(Session.class);
		return  session.createQuery("From  UserAddressBookEntity where user_id=:userid and title=:title ").setParameter("userid",userid).setParameter("title", title).uniqueResultOptional();
      
	}
	@Override
	public List<UserAddressBookEntity> getAllBook(Long userid)
	{
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery("From  UserAddressBookEntity where userId=:userid").setParameter("userid",userid).getResultList();
	}
	
	
}
