package com.nextech.erp.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.nextech.erp.dao.StatusDao;
import com.nextech.erp.model.Status;

@Repository

public class StatusDaoImpl extends SuperDaoImpl<Status> implements StatusDao{

	@Override
	public List<Status> getStatusByType(String type) throws Exception {
		
		
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Status> criteria = builder.createQuery(Status.class);
		Root<Status> userRoot = (Root<Status>) criteria.from(Status.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("type"), type),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Status> query = session.createQuery(criteria);
		return query.getResultList();
	}

}