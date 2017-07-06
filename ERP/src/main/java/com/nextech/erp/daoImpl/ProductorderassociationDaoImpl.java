package com.nextech.erp.daoImpl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.ProductorderassociationDao;
import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.model.Productorderassociation;

@Repository
@Transactional
public class ProductorderassociationDaoImpl extends
		SuperDaoImpl<Productorderassociation> implements
		ProductorderassociationDao {

	@Override
	public Productorderassociation getProductorderassociationByProdcutOrderIdandProdcutId(
			long pOrderId, long pId) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productorderassociation.class);
		criteria.add(Restrictions.eq("productorder.id", pOrderId));
		criteria.add(Restrictions.eq("product.id", pId));
		criteria.add(Restrictions.eq("isactive", true));
		Productorderassociation productorderassociation = (Productorderassociation) (criteria.list().size() > 0 ? criteria.list().get(0) : null);
		return productorderassociation;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Productorderassociation> getProductorderassociationByProdcutId(long pId) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productorderassociation.class);
		criteria.add(Restrictions.eq("product.id", pId));
		criteria.add(Restrictions.ge("remainingQuantity", new Long(0)));
		criteria.add(Restrictions.eq("isactive", true));
		return (criteria.list().size() > 0 ? (List<Productorderassociation>)criteria.list() : null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Productorderassociation> getProductorderassociationByOrderId(
			long oderID) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productorderassociation.class);
		criteria.add(Restrictions.eq("productorder.id", oderID));
		criteria.add(Restrictions.eq("isactive", true));
//		criteria.add(Restrictions.gt("remainingQuantity", new Long(0)));
		return (criteria.list().size() > 0 ? (List<Productorderassociation>)criteria.list() : null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Productorderassociation> getIncompleteProductOrderAssoByProdutId(long productId)
			throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session
				.createCriteria(Productorderassociation.class);
		criteria.add(Restrictions.eq("product.id", productId));
		criteria.add(Restrictions.eq("isactive", true));
		
		//TODO Should we add Status check here in Criteria?
		criteria.add(Restrictions.gt("remainingQuantity", new Long(0)));
		return  (criteria.list().size() > 0 ? criteria.list() : null);
	}

	@Override
	public Productionplanning getProductionPlanningforCurrentMonthByProductIdAndDate(
			long pId, Date date) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productionplanning.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("product.id", pId));
		criteria.add(Restrictions.eq("date", date));
		Productionplanning productionplanning = criteria.list().size() > 0 ? (Productionplanning) criteria
				.list().get(0) : null;
		return productionplanning;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Productorderassociation> getProductOrderAssoByOrderId(
			long orderId) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productorderassociation.class);
		criteria.add(Restrictions.eq("productorder.id", orderId));
		criteria.add(Restrictions.eq("isactive", true));
		return  (criteria.list().size() > 0 ? criteria.list() : null);
	}

	@Override
	public Productorderassociation getProdcutAssoByProdcutId(long prodcutId)
			throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productorderassociation.class);
		criteria.add(Restrictions.eq("product.id", prodcutId));
		criteria.add(Restrictions.eq("isactive", true));
		Productorderassociation productorderassociation = (Productorderassociation) (criteria.list().size() > 0 ? criteria.list().get(0) : null);
		return productorderassociation;
	}

	@Override
	public Productorderassociation getProdcutAssoByOrder(long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productorderassociation.class);
		criteria.add(Restrictions.eq("productorder.id", orderId));
		criteria.add(Restrictions.eq("isactive", true));
		Productorderassociation productorderassociation = (Productorderassociation) (criteria.list().size() > 0 ? criteria.list().get(0) : null);
		return productorderassociation;
	}

}
