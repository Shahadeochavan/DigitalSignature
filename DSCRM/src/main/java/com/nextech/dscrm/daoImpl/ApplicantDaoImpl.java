package com.nextech.dscrm.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.nextech.dscrm.dao.ApplicantDao;
import com.nextech.dscrm.model.Applicant;

@Repository
public class ApplicantDaoImpl extends SuperDaoImpl<Applicant> implements ApplicantDao {

	@Override
	public List<Applicant> getApplicantsByClientId(long clientId)
			throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Applicant> criteria = builder.createQuery(Applicant.class);
		Root<Applicant> userRoot  = (Root<Applicant>) criteria.from(Applicant.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("client"), clientId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Applicant> query = session.createQuery(criteria);
		return query.getResultList();
	}

}
