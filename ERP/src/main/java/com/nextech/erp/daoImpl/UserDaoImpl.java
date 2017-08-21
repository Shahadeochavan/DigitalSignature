package com.nextech.erp.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.UserDao;
import com.nextech.erp.model.User;

@Repository
@Transactional
public class UserDaoImpl extends SuperDaoImpl<User> implements UserDao {

	@Override
	public User getUserByUserId(String userid) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> userRoot = criteria.from(User.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("userid"), userid),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<User> query = session.createQuery(criteria);
		List<User> results = query.getResultList();
		return results.get(0);
	}

	@Override
	public User getUserByEmail(String email) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("email", email));
		User user = criteria.list().size() > 0 ? (User) criteria.list().get(0): null;
		return user;
	}

	@Override
	public User getUserByMobile(String mobile) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("mobile", mobile));
		User user = criteria.list().size() > 0 ? (User) criteria.list().get(0): null;
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserProfileByUserId(long id) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("id", id));
		return criteria.list();
	}


	@Override
	public User getUserByFirstNamLastName(String firstName, String lastName)throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("firstName", firstName));
		criteria.add(Restrictions.eq("lastName", lastName));
		User user = criteria.list().size() > 0 ? (User) criteria.list().get(0): null;
		return user;
	}

	@Override
	public User getEmailUserById(long id) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("id", id));
		User user = criteria.list().size() > 0 ? (User) criteria.list().get(0): null;
		return user;
		
	}

	@Override
	public User getUserByContact(String contact) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("userid", contact));
		User user = criteria.list().size() > 0 ? (User) criteria.list().get(0): null;
		return user;
	}

	@Override
	public User getUserByNotifictionId(long notificatinId) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("mobile", notificatinId));
		User user = criteria.list().size() > 0 ? (User) criteria.list().get(0): null;
		return user;
	}
}
