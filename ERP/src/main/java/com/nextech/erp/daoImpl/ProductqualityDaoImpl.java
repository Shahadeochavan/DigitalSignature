package com.nextech.erp.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.ProductqualityDao;
import com.nextech.erp.model.Productquality;

@Repository
@Transactional
public class ProductqualityDaoImpl extends SuperDaoImpl<Productquality> implements ProductqualityDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Productquality> getProductqualityListByProductId(long productId)
			throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productquality.class);
		criteria.add(Restrictions.eq("product.id", productId));
		criteria.add(Restrictions.eq("isactive", true));
		return (criteria.list().size() > 0 ? (List<Productquality>)criteria.list() : null);
	}
	
	

}
