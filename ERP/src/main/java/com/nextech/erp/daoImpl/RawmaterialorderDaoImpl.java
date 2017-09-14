package com.nextech.erp.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.springframework.stereotype.Repository;

import com.nextech.erp.dao.RawmaterialorderDao;
import com.nextech.erp.model.Rawmaterialorder;

@Repository

public class RawmaterialorderDaoImpl extends SuperDaoImpl<Rawmaterialorder> implements RawmaterialorderDao {

	// private static final long STATUS_RAW_MATERIAL_ORDER_INCOMPLETE=2;
	private static final long STATUS_RAW_MATERIAL_ORDER_COMPLETE = 3;

	@Override
	public List<Rawmaterialorder> getRawmaterialorderByStatusId(long statusId, long statusId1, long statusId2)
			throws Exception {
		session = sessionFactory.openSession();
		  CriteriaBuilder criteriaBuilder=session.getCriteriaBuilder();
		    CriteriaQuery<Rawmaterialorder> criteriaQuery=criteriaBuilder.createQuery(Rawmaterialorder.class);
		    Metamodel metamodel=session.getMetamodel();
		    EntityType<Rawmaterialorder> entityType = metamodel.entity(Rawmaterialorder.class);
		    Root<Rawmaterialorder> root = criteriaQuery.from(entityType);
		    criteriaQuery.where(root.get("status").in(statusId,statusId1,statusId2),criteriaBuilder.equal(root.get("isactive"), true));
		    TypedQuery<Rawmaterialorder> typedQuery = session.createQuery(criteriaQuery);
		    return typedQuery.getResultList();
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialorderByQualityCheckStatusId(long statusId) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rawmaterialorder> criteria = builder.createQuery(Rawmaterialorder.class);
		Root<Rawmaterialorder> userRoot = (Root<Rawmaterialorder>) criteria.from(Rawmaterialorder.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("status"), statusId),
				builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Rawmaterialorder> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialorderByVendor(long vendorId) throws Exception {
		/*
		 * session = sessionFactory.openSession();
		 * 
		 * @SuppressWarnings("deprecation") Criteria criteria =
		 * session.createCriteria(Rawmaterialorder.class);
		 * criteria.add(Restrictions.eq("isactive", true));
		 * criteria.add(Restrictions.eq("vendor.id", vendorId));
		 * criteria.add(Restrictions.and(Restrictions.not(Restrictions.eq(
		 * "status.id", STATUS_RAW_MATERIAL_ORDER_COMPLETE)))); return
		 * criteria.list();
		 */

		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rawmaterialorder> criteria = builder.createQuery(Rawmaterialorder.class);
		Root<Rawmaterialorder> userRoot = (Root<Rawmaterialorder>) criteria.from(Rawmaterialorder.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("vendor"), vendorId),
				builder.equal(userRoot.get("isactive"), true),
				builder.and(builder.not(builder.equal(userRoot.get("status"), STATUS_RAW_MATERIAL_ORDER_COMPLETE))));
		TypedQuery<Rawmaterialorder> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialorderByVendorId(long vendorId, long statusId1, long statusId2)
			throws Exception {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Rawmaterialorder> criteriaQuery = criteriaBuilder.createQuery(Rawmaterialorder.class);
		Metamodel metamodel = session.getMetamodel();
		EntityType<Rawmaterialorder> entityType = metamodel.entity(Rawmaterialorder.class);
		Root<Rawmaterialorder> root = criteriaQuery.from(entityType);
		criteriaQuery.where(root.get("vendor").in(vendorId, statusId1, statusId2),
				criteriaBuilder.equal(root.get("isactive"), true));
		TypedQuery<Rawmaterialorder> typedQuery = session.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
}
