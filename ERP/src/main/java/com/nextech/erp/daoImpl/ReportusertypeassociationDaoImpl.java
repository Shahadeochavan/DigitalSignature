package com.nextech.erp.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import com.nextech.erp.dao.ReportusertypeassociationDao;
import com.nextech.erp.model.Reportusertypeassociation;

@Repository

public class ReportusertypeassociationDaoImpl extends SuperDaoImpl<Reportusertypeassociation> implements ReportusertypeassociationDao {

	@Override
	public List<Reportusertypeassociation> getReportByUsertype(long usertypeId) {
		
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Reportusertypeassociation> criteria = builder.createQuery(Reportusertypeassociation.class);
		Root<Reportusertypeassociation> userRoot  = (Root<Reportusertypeassociation>) criteria.from(Reportusertypeassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("usertype"), usertypeId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Reportusertypeassociation> query = session.createQuery(criteria);
		return query.getResultList();
	}
	}
