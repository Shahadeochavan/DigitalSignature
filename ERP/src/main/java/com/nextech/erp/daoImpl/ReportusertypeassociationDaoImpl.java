package com.nextech.erp.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nextech.erp.dao.ReportusertypeassociationDao;
import com.nextech.erp.model.Reportusertypeassociation;

@Repository

public class ReportusertypeassociationDaoImpl extends SuperDaoImpl<Reportusertypeassociation> implements ReportusertypeassociationDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Reportusertypeassociation> getReportByUsertype(long usertypeId) {
		// TODO Auto-generated method stub
		session = sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Reportusertypeassociation.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("usertype.id", usertypeId));
		List<Reportusertypeassociation> reportusertypeassociations = criteria.list().size() > 0 ?  criteria.list(): null;
		return reportusertypeassociations;
	}
	}
