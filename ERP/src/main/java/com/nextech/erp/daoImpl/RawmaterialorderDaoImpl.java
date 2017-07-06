package com.nextech.erp.daoImpl;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dao.RawmaterialorderDao;
import com.nextech.erp.model.Rawmaterialorder;

@Repository
@Transactional
public class RawmaterialorderDaoImpl extends SuperDaoImpl<Rawmaterialorder>
		implements RawmaterialorderDao {

	private static final long STATUS_RAW_MATERIAL_ORDER_INCOMPLETE=2;
	private static final long STATUS_RAW_MATERIAL_ORDER_COMPLETE=3;

	
	@Override
	public Rawmaterialorder getRawmaterialorderByIdName(long id, String rmname)
			throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Rawmaterialorder.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.eq("name", rmname));
		Rawmaterialorder rawmaterialorder = criteria.list().size() > 0 ? (Rawmaterialorder) criteria.list().get(0) : null;
		return rawmaterialorder;
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialorderByStatusId(long statusId,long statusId1,long statusId2)
			throws Exception {
		session = sessionFactory.openSession();
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
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Rawmaterialorder.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("status.id", statusId));
		@SuppressWarnings("unchecked")
		List<Rawmaterialorder> rawmaterialorder = criteria.list().size() > 0 ? (List<Rawmaterialorder>) criteria.list() : null;
		return rawmaterialorder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rawmaterialorder> getRawmaterialorderByVendor(long vendorId)throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Rawmaterialorder.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("vendor.id", vendorId));
		criteria.add(Restrictions.and(Restrictions.not(Restrictions.eq("status.id", STATUS_RAW_MATERIAL_ORDER_COMPLETE))));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rawmaterialorder> getRawmaterialByName(String name)
			throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Rawmaterialorder.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("name", name));
		return criteria.list();
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialorderByVendorId(long vendorId,
			long statusId1, long statusId2) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
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

