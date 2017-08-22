package com.nextech.erp.daoImpl;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nextech.erp.dao.ProductionplanningDao;
import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.model.Productorderassociation;

@Repository

public class ProductionplanningDaoImpl extends SuperDaoImpl<Productionplanning>
		implements ProductionplanningDao {

	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public Productionplanning getProductionPlanningforCurrentMonthByProductIdAndDate(long pId, Date date) throws Exception {
		session = sessionFactory.getCurrentSession();
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productionplanning> criteria = builder.createQuery(Productionplanning.class);
		Root<Productionplanning> userRoot = (Root<Productionplanning>) criteria.from(Productionplanning.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("product"), pId),builder.equal(userRoot.get("date"), date),builder.equal(userRoot.get("date"), date),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productionplanning> query = session.createQuery(criteria);
		  List<Productionplanning> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}

	@Override
	public List<Productionplanning> getProductionplanningByCurrentMonth(
			Date month) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productionplanning> criteria = builder.createQuery(Productionplanning.class);
		Root<Productionplanning> userRoot  = (Root<Productionplanning>) criteria.from(Productionplanning.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("date"), month),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productionplanning> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Productionplanning> getProductionPlanByMonthYear(
			Date startDate, Date endDate) throws Exception {
		session = sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productionplanning.class);
		criteria.add(Restrictions.between("date", startDate, endDate));
		return criteria.list();
	}

	@Override
	public List<Productionplanning> updateProductionPlanByMonthYear(
			String month_year) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productionplanning> criteria = builder.createQuery(Productionplanning.class);
		Root<Productionplanning> userRoot  = (Root<Productionplanning>) criteria.from(Productionplanning.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("date"), month_year),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productionplanning> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public Productionplanning getProductionPlanningByDateAndProductId(
			Date productionDateStart, Date productionDateEnd, long product_id) {
		session = sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productionplanning.class);
		// criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("product.id", product_id));
		criteria.add(Restrictions.between("date", productionDateStart,
				productionDateEnd));
		Productionplanning productionplanning = criteria.list().size() > 0 ? (Productionplanning) criteria
				.list().get(0) : null;
		 //session.close();
		return productionplanning;
		
	
	}

	@Override
	public Productionplanning getProductionplanByDateAndProductId(Date date,
			long pId) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productionplanning> criteria = builder.createQuery(Productionplanning.class);
		Root<Productionplanning> userRoot = (Root<Productionplanning>) criteria.from(Productionplanning.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("product"), pId),builder.equal(userRoot.get("date"), date),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productionplanning> query = session.createQuery(criteria);
		  List<Productionplanning> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}

	@Override
	public List<Productionplanning> getProductionplanByDate(Date date)throws Exception {
		session = sessionFactory.getCurrentSession();
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productionplanning> criteria = builder.createQuery(Productionplanning.class);
		Root<Productionplanning> userRoot  = (Root<Productionplanning>) criteria.from(Productionplanning.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("date"), date),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productionplanning> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public List<Productionplanning> getProductionplanByProdutId(Date date,long productID)
			throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.getCurrentSession();
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productionplanning> criteria = builder.createQuery(Productionplanning.class);
		Root<Productionplanning> userRoot  = (Root<Productionplanning>) criteria.from(Productionplanning.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("date"), date),builder.equal(userRoot.get("product"), productID),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productionplanning> query = session.createQuery(criteria);
		return query.getResultList();
	}

}
