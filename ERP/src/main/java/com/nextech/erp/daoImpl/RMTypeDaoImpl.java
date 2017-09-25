package com.nextech.erp.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import com.nextech.erp.dao.RMTypeDao;
import com.nextech.erp.model.Rmtype;

@Repository
public class RMTypeDaoImpl extends SuperDaoImpl<Rmtype> implements RMTypeDao{

	@Override
	public Rmtype getRMTypeByRMTypeName(String rmTypeName) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rmtype> criteria = builder.createQuery(Rmtype.class);
		Root<Rmtype> userRoot = criteria.from(Rmtype.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("name"), rmTypeName),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Rmtype> query = session.createQuery(criteria);
		List<Rmtype> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}
}
