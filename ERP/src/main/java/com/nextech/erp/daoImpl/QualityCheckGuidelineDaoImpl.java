package com.nextech.erp.daoImpl;

import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import com.nextech.erp.dao.QualityCheckGuidelineDao;
import com.nextech.erp.model.Qualitycheckguideline;

@Repository
public class QualityCheckGuidelineDaoImpl extends SuperDaoImpl<Qualitycheckguideline> implements QualityCheckGuidelineDao{

	@Override
	public Qualitycheckguideline getQCGuidlineByRMId(long rmId)
			throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Qualitycheckguideline> criteria = builder.createQuery(Qualitycheckguideline.class);
		Root<Qualitycheckguideline> userRoot = (Root<Qualitycheckguideline>) criteria.from(Qualitycheckguideline.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("rawMaterialId"), rmId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Qualitycheckguideline> query = session.createQuery(criteria);
		  List<Qualitycheckguideline> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}

	@Override
	public Qualitycheckguideline getQCGuidelineByProductId(long productId)
			throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Qualitycheckguideline> criteria = builder.createQuery(Qualitycheckguideline.class);
		Root<Qualitycheckguideline> userRoot = (Root<Qualitycheckguideline>) criteria.from(Qualitycheckguideline.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("productId"), productId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Qualitycheckguideline> query = session.createQuery(criteria);
		  List<Qualitycheckguideline> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}
}
