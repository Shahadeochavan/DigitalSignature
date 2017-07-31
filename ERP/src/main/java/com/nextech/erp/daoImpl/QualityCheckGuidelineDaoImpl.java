package com.nextech.erp.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nextech.erp.dao.QualityCheckGuidelineDao;
import com.nextech.erp.model.Qualitycheckguideline;

@Repository
public class QualityCheckGuidelineDaoImpl extends SuperDaoImpl<Qualitycheckguideline> implements QualityCheckGuidelineDao{

	@Override
	public Qualitycheckguideline getQCGuidlineByRMId(long rmId)
			throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Qualitycheckguideline.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("rawMaterialId", rmId));
		Qualitycheckguideline qualitycheckguideline = criteria.list().size() > 0 ? (Qualitycheckguideline) criteria.list().get(0) : null;
		return qualitycheckguideline;
	}

	@Override
	public Qualitycheckguideline getQCGuidelineByProductId(long productId)
			throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Qualitycheckguideline.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("productId", productId));
		Qualitycheckguideline qualitycheckguideline = criteria.list().size() > 0 ? (Qualitycheckguideline) criteria.list().get(0) : null;
		return qualitycheckguideline;
	}

}
