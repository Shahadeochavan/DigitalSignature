package com.nextech.erp.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.RawmaterialorderassociationDao;
import com.nextech.erp.model.Rawmaterialorderassociation;

@Repository
@Transactional
public class RawmaterialorderassociationDaoImpl extends SuperDaoImpl<Rawmaterialorderassociation> implements
		RawmaterialorderassociationDao {
	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<Rawmaterialorderassociation> getRMOrderRMAssociationByRMOrderId(long id) throws Exception {
		session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Rawmaterialorderassociation.class);
		criteria.add(Restrictions.eq("rawmaterialorder.id", id));
		criteria.add(Restrictions.eq("isactive", true));
		//criteria.add(Restrictions.gt("remainingQuantity", new Long(0)));
		return (criteria.list().size() > 0 ? (List<Rawmaterialorderassociation>)criteria.list() : null);
	}

	@Override
	public Rawmaterialorderassociation getRMOrderRMAssociationByRMOrderIdandRMId(long id, long rmId) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Rawmaterialorderassociation.class);
		criteria.add(Restrictions.eq("rawmaterialorder.id", id));
		criteria.add(Restrictions.eq("rawmaterial.id", rmId));
		criteria.add(Restrictions.eq("isactive", true));
		Rawmaterialorderassociation rawmaterialorderassociation = (Rawmaterialorderassociation) (criteria.list().size()>0 ? criteria.list().get(0) : null);
		return rawmaterialorderassociation;
	}

}
