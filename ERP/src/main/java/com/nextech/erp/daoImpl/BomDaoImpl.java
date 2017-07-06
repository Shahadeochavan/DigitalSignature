package com.nextech.erp.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.BomDao;
import com.nextech.erp.model.Bom;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productrawmaterialassociation;

@Repository
@Transactional
public class BomDaoImpl extends SuperDaoImpl<Bom> implements BomDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Bom> getBomListByProductId(long productID) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Bom.class);
		criteria.add(Restrictions.eq("product.id", productID));
		criteria.add(Restrictions.eq("isactive", true));
		return (List<Bom>) (criteria.list().size() > 0 ? criteria.list() : null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bom> getBomListByProductIdAndBomId(long productId, long bomId)
			throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Bom.class);
		criteria.add(Restrictions.eq("product.id", productId));
		criteria.add(Restrictions.eq("id", bomId));
		criteria.add(Restrictions.eq("isactive", true));
		return (List<Bom>) (criteria.list().size() > 0 ? criteria.list() : null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getProductList() {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productrawmaterialassociation.class);
		
		criteria.setProjection(Projections.distinct(Projections.property("product.id")));
		criteria.add(Restrictions.eq("isactive", true));
		return (List<Long>) (criteria.list().size() > 0 ? criteria.list() : null);
	}

	@Override
	public Bom getBomByProductId(long productID) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Bom.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("product.id", productID));
		Bom bom = criteria.list().size() > 0 ? (Bom) criteria.list().get(0) : null;
		return bom;
	}

}
