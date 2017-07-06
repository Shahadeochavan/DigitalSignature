package com.nextech.erp.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.BOMRMVendorAssociationDao;
import com.nextech.erp.model.Bomrmvendorassociation;

@Repository
@Transactional
public class BOMRMVendorAssociationDaoImpl extends
		SuperDaoImpl<Bomrmvendorassociation> implements
		BOMRMVendorAssociationDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Bomrmvendorassociation> getBomRMVendorByBomId(long bomId)
			throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session
				.createCriteria(Bomrmvendorassociation.class);
		criteria.add(Restrictions.eq("bom.id", bomId));
		criteria.add(Restrictions.eq("isactive", true));
		return (List<Bomrmvendorassociation>) (criteria.list().size() > 0 ? criteria
				.list() : null);
	}
}
