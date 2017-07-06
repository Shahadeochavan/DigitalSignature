package com.nextech.erp.daoImpl;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.UserDao;
import com.nextech.erp.model.Productorder;
import com.nextech.erp.model.User;

@Repository
@Transactional
public class UserDaoImpl extends SuperDaoImpl<User> implements UserDao {

	@Override
	public User getUserByUserId(String userid) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("userid", userid));
		System.out.println("UserDaoImpl session closed session.isOpen() : " + session.isOpen() + " sessionFactory.isOpen() : " + sessionFactory.isOpen());
		User user = criteria.list().size() > 0 ? (User) criteria.list().get(0): null;
		return user;
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
		// TODO Auto-generated method stub
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
		System.out.println("UserDaoImpl session closed session.isOpen() : " + session.isOpen() + " sessionFactory.isOpen() : " + sessionFactory.isOpen());
		User user = criteria.list().size() > 0 ? (User) criteria.list().get(0): null;
		return user;
	}

	@Override
	public User getEmailUserById(long id) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("id", id));
		criteria .setProjection(Projections.projectionList()
			      .add(Projections.property("email"), "email"))
			    .setResultTransformer(Transformers.aliasToBean(User.class));
		User user = criteria.list().size() > 0 ? (User) criteria.list().get(0): null;
		return user;
		
	}

	@Override
	public User getUserByContact(String contact) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("userid", contact));
		System.out.println("UserDaoImpl session closed session.isOpen() : " + session.isOpen() + " sessionFactory.isOpen() : " + sessionFactory.isOpen());
		User user = criteria.list().size() > 0 ? (User) criteria.list().get(0): null;
		return user;
	}
}
