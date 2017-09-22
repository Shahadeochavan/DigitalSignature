package com.nextech.erp.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import com.nextech.erp.dao.UnitDao;
import com.nextech.erp.model.Unit;

@Repository

public class UnitDaoImpl extends SuperDaoImpl<Unit> implements UnitDao {

	@Override
	public Unit getUnitByName(String name) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Unit> criteria = builder.createQuery(Unit.class);
		Root<Unit> userRoot = criteria.from(Unit.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("name"), name),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Unit> query = session.createQuery(criteria);
		List<Unit> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}
}
