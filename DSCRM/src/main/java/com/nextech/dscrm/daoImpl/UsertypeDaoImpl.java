package com.nextech.dscrm.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.nextech.dscrm.dao.UserTypeDao;
import com.nextech.dscrm.model.Usertype;

@Repository

public class UsertypeDaoImpl extends SuperDaoImpl<Usertype> implements UserTypeDao {

	@Override
	public Usertype getUserTypeByUserTypeName(String userTypeName)
			throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Usertype> criteria = builder.createQuery(Usertype.class);
		Root<Usertype> userRoot = criteria.from(Usertype.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("usertypeName"), userTypeName),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Usertype> query = session.createQuery(criteria);
		List<Usertype> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}
	
}

