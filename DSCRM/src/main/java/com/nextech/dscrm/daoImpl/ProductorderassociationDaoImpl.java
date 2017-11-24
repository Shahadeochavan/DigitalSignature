package com.nextech.dscrm.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.nextech.dscrm.dao.ProductorderassociationDao;
import com.nextech.dscrm.model.Productorderassociation;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Productorderassociation> getProductorderassociationByProductId(long pId) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productorderassociation> criteria = builder.createQuery(Productorderassociation.class);
		Root<Productorderassociation> userRoot  = (Root<Productorderassociation>) criteria.from(Productorderassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("product"), pId),builder.greaterThan((Path<Comparable>) userRoot.<Comparable> get("remainingQuantity"),
				(Comparable) new Long(0)/*userRoot.get("remainingQuantity"),  new Long(0)*/),builder.equal(userRoot.get("isactive"), true));
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
	public List<Productorderassociation> getIncompleteProductOrderAssoByProductId(long productId)
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
	public Productorderassociation getProdcutAssoByProductId(long prodcutId)
			throws Exception {
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

	@Override
	public List<Productorderassociation> getIncompleteProductOrderAssociations() throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productorderassociation> criteria = builder.createQuery(Productorderassociation.class);
		Root<Productorderassociation> userRoot = (Root<Productorderassociation>) criteria.from(Productorderassociation.class);
		criteria.select(userRoot).where(builder.notEqual(userRoot.get("remainingQuantity"), 0),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productorderassociation> query = session.createQuery(criteria);
		List<Productorderassociation> list = query.getResultList();
		return list.isEmpty() ? null : list;
	}
}
