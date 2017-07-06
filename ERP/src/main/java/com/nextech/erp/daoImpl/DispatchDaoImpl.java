package com.nextech.erp.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.DispatchDao;
import com.nextech.erp.model.Dispatch;

@Repository
@Transactional
public class DispatchDaoImpl extends SuperDaoImpl<Dispatch> implements DispatchDao {

	@Override
	public Dispatch getDispatchByProductOrderIdAndProductId(long orderID,
			long productID) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Dispatch.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("productorder.id", orderID));
		criteria.add(Restrictions.eq("product.id", productID));
		Dispatch dispatch = criteria.list().size() > 0 ? (Dispatch) criteria.list().get(0): null;
		return dispatch;
	}

	@Override
	public List<Dispatch> getDispatchByProductOrderId(long productOrderId)
			throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Dispatch.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("productorder.id", productOrderId));
		return (criteria.list().size() > 0 ? (List<Dispatch>)criteria.list() : null);
	}

}
