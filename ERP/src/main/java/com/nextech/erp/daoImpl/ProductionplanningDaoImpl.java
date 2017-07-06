package com.nextech.erp.daoImpl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.ProductionplanningDao;
import com.nextech.erp.model.Productionplanning;

@Repository
@Transactional
public class ProductionplanningDaoImpl extends SuperDaoImpl<Productionplanning>
		implements ProductionplanningDao {

	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public Productionplanning getProductionPlanningforCurrentMonthByProductIdAndDate(long pId, Date date) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productionplanning.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("product.id", pId));
		criteria.add(Restrictions.eq("date", date));
		Productionplanning productionplanning = criteria.list().size() > 0 ? (Productionplanning) criteria.list().get(0) : null;
		return productionplanning;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Productionplanning> getProductionplanningByCurrentMonth(
			Date month) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productionplanning.class);
		criteria.add(Restrictions.eq("date", month));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Productionplanning> getProductionPlanByMonthYear(
			Date startDate, Date endDate) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productionplanning.class);
		criteria.add(Restrictions.between("date", startDate, endDate));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Productionplanning> updateProductionPlanByMonthYear(
			String month_year) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productionplanning.class);
		criteria.add(Restrictions.eq("date", month_year));
		return criteria.list();
	}

	@Override
	public Productionplanning getProductionPlanningByDateAndProductId(
			Date productionDateStart, Date productionDateEnd, long product_id) {
		session = sessionFactory.openSession();
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
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productionplanning.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("product.id", pId));
		criteria.add(Restrictions.eq("date", date));
		Productionplanning productionplanning = criteria.list().size() > 0 ? (Productionplanning) criteria
				.list().get(0) : null;
		 //session.close();
		return productionplanning;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Productionplanning> getProductionplanByDate(Date date)throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productionplanning.class);
		criteria.add(Restrictions.eq("date", date));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Productionplanning> getProductionplanByProdutId(Date date,long productID)
			throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productionplanning.class);
		criteria.add(Restrictions.eq("product.id", productID));
		criteria.add(Restrictions.eq("date", date));
		criteria.add(Restrictions.eq("isactive", true));
		return criteria.list();
	}

}
