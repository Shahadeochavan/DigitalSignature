package com.nextech.erp.daoImpl;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import com.nextech.erp.dao.ProductorderassociationDao;
import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.model.Productorderassociation;

@Repository

public class ProductorderassociationDaoImpl extends
		SuperDaoImpl<Productorderassociation> implements
		ProductorderassociationDao {

	@Override
	public Productorderassociation getProductorderassociationByProdcutOrderIdandProdcutId(
			long pOrderId, long pId) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productorderassociation> criteria = builder.createQuery(Productorderassociation.class);
		Root<Productorderassociation> userRoot = (Root<Productorderassociation>) criteria.from(Productorderassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("product"), pId),builder.equal(userRoot.get("productorder"), pOrderId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productorderassociation> query = session.createQuery(criteria);
		  List<Productorderassociation> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);

	}

	@Override
	public List<Productorderassociation> getProductorderassociationByProdcutId(long pId) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productorderassociation> criteria = builder.createQuery(Productorderassociation.class);
		Root<Productorderassociation> userRoot  = (Root<Productorderassociation>) criteria.from(Productorderassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("product"), pId),builder.equal(userRoot.get("remainingQuantity"),  new Long(0)),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productorderassociation> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public List<Productorderassociation> getProductorderassociationByOrderId(
			long oderID) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productorderassociation> criteria = builder.createQuery(Productorderassociation.class);
		Root<Productorderassociation> userRoot  = (Root<Productorderassociation>) criteria.from(Productorderassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("productorder"), oderID),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productorderassociation> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public List<Productorderassociation> getIncompleteProductOrderAssoByProdutId(long productId)
			throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productorderassociation> criteria = builder.createQuery(Productorderassociation.class);
		Root<Productorderassociation> userRoot  = (Root<Productorderassociation>) criteria.from(Productorderassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("product"), productId),builder.notEqual(userRoot.get("remainingQuantity"),  new Long(0)),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productorderassociation> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public Productionplanning getProductionPlanningforCurrentMonthByProductIdAndDate(
			long pId, Date date) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productionplanning> criteria = builder.createQuery(Productionplanning.class);
		Root<Productionplanning> userRoot = (Root<Productionplanning>) criteria.from(Productionplanning.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("product"), pId),builder.equal(userRoot.get("date"), date),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productionplanning> query = session.createQuery(criteria);
		  List<Productionplanning> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}

	@Override
	public List<Productorderassociation> getProductOrderAssoByOrderId(
			long orderId) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productorderassociation> criteria = builder.createQuery(Productorderassociation.class);
		Root<Productorderassociation> userRoot  = (Root<Productorderassociation>) criteria.from(Productorderassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("productorder"), orderId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productorderassociation> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public Productorderassociation getProdcutAssoByProdcutId(long prodcutId)
			throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productorderassociation> criteria = builder.createQuery(Productorderassociation.class);
		Root<Productorderassociation> userRoot = (Root<Productorderassociation>) criteria.from(Productorderassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("product"), prodcutId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productorderassociation> query = session.createQuery(criteria);
		  List<Productorderassociation> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}

	@Override
	public Productorderassociation getProdcutAssoByOrder(long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productorderassociation> criteria = builder.createQuery(Productorderassociation.class);
		Root<Productorderassociation> userRoot = (Root<Productorderassociation>) criteria.from(Productorderassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("productorder"), orderId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productorderassociation> query = session.createQuery(criteria);
		  List<Productorderassociation> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}

}
