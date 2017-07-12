package com.nextech.erp.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nextech.erp.dao.RMVAssoDao;
import com.nextech.erp.model.Rawmaterialvendorassociation;

@Repository

public class RMVAssoDaoImpl extends SuperDaoImpl<Rawmaterialvendorassociation>
		implements RMVAssoDao {

	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public Rawmaterialvendorassociation getRMVAssoByVendorIdRMId(long vendorId,
			long rmId) throws Exception {
		session = sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Rawmaterialvendorassociation.class);
		criteria.add(Restrictions.eq("rawmaterial.id", rmId));
		criteria.add(Restrictions.eq("vendor.id", vendorId));
		Rawmaterialvendorassociation rawmaterialorderassociation = (Rawmaterialvendorassociation) (criteria.list().size() > 0 ? criteria.list().get(0) : null);
		return rawmaterialorderassociation;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rawmaterialvendorassociation> getRawmaterialvendorassociationListByRMId(long id) {
		session = sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Rawmaterialvendorassociation.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("rawmaterial.id", id));
		return (criteria.list().size() > 0 ? (List<Rawmaterialvendorassociation>)criteria.list() : null);
	}

	@Override
	public Rawmaterialvendorassociation getRMVAssoByRMId(long rmId)
			throws Exception {
		session = sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Rawmaterialvendorassociation.class);
		criteria.add(Restrictions.eq("rawmaterial.id", rmId));
		Rawmaterialvendorassociation rawmaterialorderassociation = (Rawmaterialvendorassociation) (criteria.list().size() > 0 ? criteria.list().get(0) : null);
		return rawmaterialorderassociation;
	}

}
