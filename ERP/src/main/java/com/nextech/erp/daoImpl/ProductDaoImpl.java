package com.nextech.erp.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nextech.erp.dao.ProductDao;
import com.nextech.erp.model.Product;

@Repository

public class ProductDaoImpl extends SuperDaoImpl<Product> implements ProductDao {

	@Override
	public Product getProductByName(String productname) throws Exception {
		session = sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Product.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("name", productname));
		Product product = criteria.list().size() > 0 ? (Product) criteria.list().get(0) : null;
		return product;
	}

	@Override
	public Product getProductByPartNumber(String partNumber) throws Exception {
		session = sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Product.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("partNumber", partNumber));
		Product product = criteria.list().size() > 0 ? (Product) criteria.list().get(0) : null;
		return product;
	}

	@Override
	public List<Product> getProductList(List<Long> productIdList) {
		session = sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Product.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.in("id", productIdList));
		@SuppressWarnings("unchecked")
		List<Product> products = criteria.list().size() > 0 ? (List<Product>) criteria.list() : null;
		return products;
	}

	@Override
	public Product getProductByProductId(long productId) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Product.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("id", productId));
		Product product = criteria.list().size() > 0 ? (Product) criteria.list().get(0) : null;
		return product;
	}

}
