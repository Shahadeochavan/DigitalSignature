package com.nextech.dscrm.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.nextech.dscrm.dao.DispatchDao;
import com.nextech.dscrm.model.Dispatch;

@Repository

public class DispatchDaoImpl extends SuperDaoImpl<Dispatch> implements DispatchDao {

	@Override
	public Dispatch getDispatchByProductOrderIdAndProductId(long orderID,
			long productID) throws Exception {
		
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Dispatch> criteria = builder.createQuery(Dispatch.class);
		Root<Dispatch> userRoot = (Root<Dispatch>) criteria.from(Dispatch.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("productorder"), orderID),builder.equal(userRoot.get("product"), productID),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Dispatch> query = session.createQuery(criteria);
		  List<Dispatch> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}

	@Override
	public List<Dispatch> getDispatchByProductOrderId(long productOrderId)
			throws Exception {
		
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Dispatch> criteria = builder.createQuery(Dispatch.class);
		Root<Dispatch> userRoot  = (Root<Dispatch>) criteria.from(Dispatch.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("productorder"), productOrderId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Dispatch> query = session.createQuery(criteria);
		return query.getResultList();
	}

}
