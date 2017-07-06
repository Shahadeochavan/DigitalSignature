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

import com.nextech.erp.dao.RawmaterialDao;
import com.nextech.erp.model.Qualitycheckrawmaterial;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialvendorassociation;

@Repository
@Transactional
public class RawmaterialDaoImpl extends SuperDaoImpl<Rawmaterial> implements RawmaterialDao{
	
	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Rawmaterialvendorassociation> getRawmaterialByVenodrId(long id)
			throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Rawmaterialvendorassociation.class);
		criteria.add(Restrictions.eq("vendor.id", id));
		return criteria.list();
	}

	@Override
	public Rawmaterial getRMByRMId(long id) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Rawmaterial.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("id", id));
		Rawmaterial rawmaterial = criteria.list().size() > 0 ? (Rawmaterial) criteria.list().get(0): null;
		return rawmaterial;
	}
	
}

