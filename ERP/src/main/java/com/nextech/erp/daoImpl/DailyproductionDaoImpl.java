package com.nextech.erp.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dao.DailyproductionDao;
import com.nextech.erp.model.Dailyproduction;

@Repository
@Transactional
public class DailyproductionDaoImpl extends SuperDaoImpl<Dailyproduction> implements DailyproductionDao{

	@Autowired
	private MessageSource messageSource;
	@SuppressWarnings("unchecked")
	@Override
	public List<Dailyproduction> getDailyProdPendingForQualityCheckByPlanningId(long planningId) {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Dailyproduction.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("productionplanning.id", planningId));
		criteria.add(Restrictions.eq("status.id", Long.valueOf(messageSource.getMessage(ERPConstants.STATUS_QUALITY_CHECK_PENDING, null, null))));
		return (criteria.list().size() > 0 ? criteria.list(): null);
	}

}
