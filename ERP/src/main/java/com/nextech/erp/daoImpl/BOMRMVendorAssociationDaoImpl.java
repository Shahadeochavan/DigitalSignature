package com.nextech.erp.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import com.nextech.erp.dao.BOMRMVendorAssociationDao;
import com.nextech.erp.model.Bomrmvendorassociation;

@Repository

public class BOMRMVendorAssociationDaoImpl extends
		SuperDaoImpl<Bomrmvendorassociation> implements
		BOMRMVendorAssociationDao {

	@Override
	public List<Bomrmvendorassociation> getBomRMVendorByBomId(long bomId)
			throws Exception {

		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Bomrmvendorassociation> criteria = builder.createQuery(Bomrmvendorassociation.class);
		Root<Bomrmvendorassociation> userRoot  = (Root<Bomrmvendorassociation>) criteria.from(Bomrmvendorassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("bom"), bomId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Bomrmvendorassociation> query = session.createQuery(criteria);
		return query.getResultList();
	}
}
