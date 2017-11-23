package com.nextech.dscrm.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.nextech.dscrm.dao.ProductqualityDao;
import com.nextech.dscrm.model.Productquality;

@Repository

public class ProductqualityDaoImpl extends SuperDaoImpl<Productquality> implements ProductqualityDao{

	@Override
	public List<Productquality> getProductqualityListByProductId(long productId)
			throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productquality> criteria = builder.createQuery(Productquality.class);
		Root<Productquality> userRoot  = (Root<Productquality>) criteria.from(Productquality.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("product"), productId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productquality> query = session.createQuery(criteria);
		return query.getResultList();
	}
}
