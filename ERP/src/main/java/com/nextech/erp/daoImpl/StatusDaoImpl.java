package com.nextech.erp.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.nextech.erp.dao.StatusDao;
import com.nextech.erp.model.Status;

@Repository

public class StatusDaoImpl extends SuperDaoImpl<Status> implements StatusDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Status> getStatusByType(String type) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Status.class);
		criteria.add(Restrictions.eq("type", type));
		criteria.add(Restrictions.eq("isactive", true));
		return (criteria.list().size() > 0 ? (List<Status>)criteria.list() : null);
	}

}