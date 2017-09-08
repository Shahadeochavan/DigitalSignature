package com.nextech.erp.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.nextech.erp.dao.RawmaterialinventoryDao;
import com.nextech.erp.model.Rawmaterialinventory;

@Repository

public class RawmaterialinventoryDaoImpl extends SuperDaoImpl<Rawmaterialinventory>implements RawmaterialinventoryDao {
	
	@Override
	public Rawmaterialinventory getByRMId(long id) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rawmaterialinventory> criteria = builder.createQuery(Rawmaterialinventory.class);
		Root<Rawmaterialinventory> userRoot = criteria.from(Rawmaterialinventory.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("rawmaterial"), id),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Rawmaterialinventory> query = session.createQuery(criteria);
		List<Rawmaterialinventory> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}
}
