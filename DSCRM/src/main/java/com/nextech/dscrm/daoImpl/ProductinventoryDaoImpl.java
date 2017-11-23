package com.nextech.dscrm.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.nextech.dscrm.dao.ProductinventoryDao;
import com.nextech.dscrm.model.Productinventory;

@Repository

public class ProductinventoryDaoImpl extends SuperDaoImpl<Productinventory>
		implements ProductinventoryDao {

	@Override
	public Productinventory getProductinventoryByProductId(long productId)
			throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productinventory> criteria = builder.createQuery(Productinventory.class);
		Root<Productinventory> userRoot = (Root<Productinventory>) criteria.from(Productinventory.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("product"), productId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productinventory> query = session.createQuery(criteria);
		  List<Productinventory> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}

	@Override
	public List<Productinventory> getProductinventoryListByProductId(
			long productId) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productinventory> criteria = builder.createQuery(Productinventory.class);
		Root<Productinventory> userRoot  = (Root<Productinventory>) criteria.from(Productinventory.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("product"), productId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productinventory> query = session.createQuery(criteria);
		return query.getResultList();
	}
}
