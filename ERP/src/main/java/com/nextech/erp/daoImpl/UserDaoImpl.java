package com.nextech.erp.daoImpl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.fabric.xmlrpc.base.Array;
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
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}

	@Override
	public User getUserByEmail(String email) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> userRoot = criteria.from(User.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("email"), email),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<User> query = session.createQuery(criteria);
		List<User> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}

	@Override
	public User getUserByMobile(String mobile) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> userRoot = criteria.from(User.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("mobile"), mobile),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<User> query = session.createQuery(criteria);
		List<User> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}

	@Override
	public List<User> getUserProfileByUserId(long id) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> userRoot  = (Root<User>) criteria.from(User.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("id"), id),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<User> query = session.createQuery(criteria);
		return query.getResultList();
	}


	@Override
	public User getUserByFirstNamLastName(String firstName, String lastName)throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> userRoot = criteria.from(User.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("firstName"), firstName),builder.equal(userRoot.get("lastName"), lastName),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<User> query = session.createQuery(criteria);
		List<User> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}

	@Override
	public User getEmailUserById(long id) throws Exception {	
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> userRoot = criteria.from(User.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("id"), id),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<User> query = session.createQuery(criteria);
		List<User> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
		
	}

	@Override
	public User getUserByContact(String contact) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> userRoot = criteria.from(User.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("userid"), contact),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<User> query = session.createQuery(criteria);
		List<User> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}

	@Override
	public User getUserByNotifictionId(long notificatinId) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> userRoot = criteria.from(User.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("mobile"), notificatinId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<User> query = session.createQuery(criteria);
		List<User> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}

	@Override
	public List<User> getMultipleUsersById(String userId) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("isactive", true));
		long[] id = {200,201};
		Long[] longs = ArrayUtils.toObject(id);
		List<Long> list = Arrays.asList(longs);
 		Criterion criterion = Restrictions.in("id", list);
		criteria.add(Restrictions.and(criterion));
		@SuppressWarnings("unchecked")
		List<User> userList = criteria.list().size() > 0 ? (List<User>) criteria.list() : null;
		return userList;
	}
}
