package com.nextech.erp.daoImpl;

import java.util.Arrays;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nextech.erp.dao.ProductorderDao;
import com.nextech.erp.model.Productorder;

@Repository

public class ProductorderDaoImpl extends SuperDaoImpl<Productorder> implements
		ProductorderDao {

	@Override
	public Productorder getProductorderByProductOrderId(long pOrderId)throws Exception {
		
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productorder> criteria = builder.createQuery(Productorder.class);
		Root<Productorder> userRoot = (Root<Productorder>) criteria.from(Productorder.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("id"), pOrderId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productorder> query = session.createQuery(criteria);
		  List<Productorder> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
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
		
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productorder.class);
		criteria.add(Restrictions.eq("client.id", clientId));
		Criterion criterion = Restrictions.in("status.id", Arrays.asList(statusId,statusId1));
		criteria.add(Restrictions.and(criterion));
		criteria.add(Restrictions.eq("isactive", true));
		return (List<Productorder>) (criteria.list().size() > 0 ? criteria.list() : null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Productorder> getInCompleteProductOrders(long statusId) {
		
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productorder.class);
		Criterion criterion = Restrictions.not(Restrictions.eq("status.id", statusId));
		criteria.add(Restrictions.and(criterion));
		criteria.add(Restrictions.eq("isactive", true));
		return (List<Productorder>) (criteria.list().size() > 0 ? criteria.list() : null);
	}

}