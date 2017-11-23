package com.nextech.dscrm.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.nextech.dscrm.dao.ProductDao;
import com.nextech.dscrm.model.Product;

@Repository

public class ProductDaoImpl extends SuperDaoImpl<Product> implements ProductDao {

	@Override
	public Product getProductByName(String productname) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
		Root<Product> userRoot = criteria.from(Product.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("name"), productname),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Product> query = session.createQuery(criteria);
		  List<Product> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}

	@Override
	public Product getProductByPartNumber(String partNumber) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
		Root<Product> userRoot = criteria.from(Product.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("partNumber"), partNumber),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Product> query = session.createQuery(criteria);
		  List<Product> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}

	@Override
	public List<Product> getProductList(List<Long> productIdList) {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
		Root<Product> userRoot  = criteria.from(Product.class);
		criteria.select(userRoot).where(userRoot.in(productIdList));
		TypedQuery<Product> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public Product getProductByProductId(long productId) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
		Root<Product> userRoot = criteria.from(Product.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("id"), productId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Product> query = session.createQuery(criteria);
		  List<Product> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}

}
