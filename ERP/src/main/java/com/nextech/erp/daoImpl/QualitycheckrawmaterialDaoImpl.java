package com.nextech.erp.daoImpl;


import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import com.nextech.erp.dao.QualitycheckrawmaterialDao;
import com.nextech.erp.model.Qualitycheckrawmaterial;

@Repository

public class QualitycheckrawmaterialDaoImpl extends
		SuperDaoImpl<Qualitycheckrawmaterial> implements
		QualitycheckrawmaterialDao {

	@Override
	public Qualitycheckrawmaterial getQualitycheckrawmaterialByInvoiceIdAndRMId(long invoiceId,long rmId) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Qualitycheckrawmaterial> criteria = builder.createQuery(Qualitycheckrawmaterial.class);
		Root<Qualitycheckrawmaterial> userRoot = criteria.from(Qualitycheckrawmaterial.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("rawmaterial"), rmId),builder.equal(userRoot.get("rawmaterialorderinvoice"), invoiceId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Qualitycheckrawmaterial> query = session.createQuery(criteria);
		List<Qualitycheckrawmaterial> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
}

	@Override
	public List<Qualitycheckrawmaterial> getQualitycheckrawmaterialByInvoiceId(
			long invoiceId) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Qualitycheckrawmaterial> criteria = builder.createQuery(Qualitycheckrawmaterial.class);
		Root<Qualitycheckrawmaterial> userRoot  = (Root<Qualitycheckrawmaterial>) criteria.from(Qualitycheckrawmaterial.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("rawmaterialorderinvoice"), invoiceId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Qualitycheckrawmaterial> query = session.createQuery(criteria);
		return query.getResultList();
	}
}
