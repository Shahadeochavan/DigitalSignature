package com.nextech.dscrm.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.nextech.dscrm.dao.ReptOptAssoDao;
import com.nextech.dscrm.model.Reportoutputassociation;

@Repository

public class ReptOptAssoDaoImpl extends SuperDaoImpl<Reportoutputassociation> implements ReptOptAssoDao {

	@Override
	public List<Reportoutputassociation> getListByReportId(long id) {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Reportoutputassociation> criteria = builder.createQuery(Reportoutputassociation.class);
		Root<Reportoutputassociation> userRoot  = (Root<Reportoutputassociation>) criteria.from(Reportoutputassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("report"), id),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Reportoutputassociation> query = session.createQuery(criteria);
		return query.getResultList();
	}

}
