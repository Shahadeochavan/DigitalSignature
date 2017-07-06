package com.nextech.erp.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.ReptInpAssoDao;
import com.nextech.erp.model.Reportinputassociation;

@Repository
@Transactional
public class ReptInpAssoDaoImpl extends SuperDaoImpl<Reportinputassociation> implements ReptInpAssoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Reportinputassociation> getListByReportId(long id) {
		try {
			session = sessionFactory.openSession();
			@SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(Reportinputassociation.class);
			criteria.add(Restrictions.eq("report.id",id));
			criteria.add(Restrictions.eq("isactive", true));
			return (List<Reportinputassociation> ) criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
