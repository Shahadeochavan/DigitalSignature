package com.nextech.erp.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dao.DailyproductionDao;
import com.nextech.erp.model.Dailyproduction;

@Repository

public class DailyproductionDaoImpl extends SuperDaoImpl<Dailyproduction> implements DailyproductionDao{

	@Autowired
	private MessageSource messageSource;
	
	@Override
	public List<Dailyproduction> getDailyProdPendingForQualityCheckByPlanningId(long planningId) {
		
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Dailyproduction> criteria = builder.createQuery(Dailyproduction.class);
		Root<Dailyproduction> userRoot  = (Root<Dailyproduction>) criteria.from(Dailyproduction.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("productionplanning"), planningId),builder.equal(userRoot.get("status"), Long.valueOf(messageSource.getMessage(ERPConstants.STATUS_QUALITY_CHECK_PENDING, null, null))),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Dailyproduction> query = session.createQuery(criteria);
		return query.getResultList();
	}

}
