package com.nextech.erp.daoImpl;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.ProductorderDao;
import com.nextech.erp.model.Productorder;

@Repository
@Transactional
public class ProductorderDaoImpl extends SuperDaoImpl<Productorder> implements
		ProductorderDao {

	@Override
	public Productorder getProductorderByProductOrderId(long pOrderId)throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productorder.class);
		criteria.add(Restrictions.eq("id", pOrderId));
		criteria.add(Restrictions.eq("isactive", true));
		Productorder productorder = (Productorder) (criteria.list().size() > 0 ? criteria.list().get(0) : null);
		return productorder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Productorder> getPendingProductOrders(long statusId,long statusId1) {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productorder.class);
		criteria.add(Restrictions.eq("isactive", true));
		Criterion criterion = Restrictions.in("status.id", Arrays.asList(statusId,statusId1));
		criteria.add(Restrictions.and(criterion));
		return (List<Productorder>) (criteria.list().size() > 0 ? criteria.list() : null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Productorder> getInCompleteProductOrder(long clientId,long statusId,long statusId1) {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productorder.class);
		criteria.add(Restrictions.eq("client.id", clientId));
		Criterion criterion = Restrictions.in("status.id", Arrays.asList(statusId,statusId1));
		criteria.add(Restrictions.and(criterion));
		criteria.add(Restrictions.eq("isactive", true));
		return (List<Productorder>) (criteria.list().size() > 0 ? criteria.list() : null);
	}

}