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

import com.nextech.erp.dao.RawmaterialorderDao;
import com.nextech.erp.model.Rawmaterialorder;

@Repository

public class RawmaterialorderDaoImpl extends SuperDaoImpl<Rawmaterialorder>
		implements RawmaterialorderDao {

//	private static final long STATUS_RAW_MATERIAL_ORDER_INCOMPLETE=2;
	private static final long STATUS_RAW_MATERIAL_ORDER_COMPLETE=3;

	
	@Override
	public Rawmaterialorder getRawmaterialorderByIdName(long id, String rmname)
			throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rawmaterialorder> criteria = builder.createQuery(Rawmaterialorder.class);
		Root<Rawmaterialorder> userRoot = criteria.from(Rawmaterialorder.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("name"), rmname),builder.equal(userRoot.get("id"), id),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Rawmaterialorder> query = session.createQuery(criteria);
		List<Rawmaterialorder> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialorderByStatusId(long statusId,long statusId1,long statusId2)
			throws Exception {
		
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Rawmaterialorder.class);
		criteria.add(Restrictions.eq("isactive", true));
		Criterion criterion = Restrictions.in("status.id", Arrays.asList(statusId,statusId1,statusId2));
		criteria.add(Restrictions.and(criterion));
		@SuppressWarnings("unchecked")
		List<Rawmaterialorder> rawmaterialorder = criteria.list().size() > 0 ? (List<Rawmaterialorder>) criteria.list() : null;
		return rawmaterialorder;
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialorderByQualityCheckStatusId(long statusId) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rawmaterialorder> criteria = builder.createQuery(Rawmaterialorder.class);
		Root<Rawmaterialorder> userRoot  = (Root<Rawmaterialorder>) criteria.from(Rawmaterialorder.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("status"), statusId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Rawmaterialorder> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rawmaterialorder> getRawmaterialorderByVendor(long vendorId)throws Exception {
		
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Rawmaterialorder.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("vendor.id", vendorId));
		criteria.add(Restrictions.and(Restrictions.not(Restrictions.eq("status.id", STATUS_RAW_MATERIAL_ORDER_COMPLETE))));
		return criteria.list();
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialByName(String name)
			throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rawmaterialorder> criteria = builder.createQuery(Rawmaterialorder.class);
		Root<Rawmaterialorder> userRoot  = (Root<Rawmaterialorder>) criteria.from(Rawmaterialorder.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("name"), name),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Rawmaterialorder> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialorderByVendorId(long vendorId,
			long statusId1, long statusId2) throws Exception {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Rawmaterialorder.class);
		criteria.add(Restrictions.eq("isactive", true));
		Criterion criterion = Restrictions.in("vendor.id", Arrays.asList(vendorId,statusId1,statusId2));
		criteria.add(Restrictions.and(criterion));
		@SuppressWarnings("unchecked")
		List<Rawmaterialorder> rawmaterialorder = criteria.list().size() > 0 ? (List<Rawmaterialorder>) criteria.list() : null;
		return rawmaterialorder;
	}
	}

