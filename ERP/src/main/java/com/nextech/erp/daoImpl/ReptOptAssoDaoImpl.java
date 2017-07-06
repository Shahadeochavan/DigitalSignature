package com.nextech.erp.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.ReptOptAssoDao;
import com.nextech.erp.model.Reportoutputassociation;

@Repository
@Transactional
public class ReptOptAssoDaoImpl extends SuperDaoImpl<Reportoutputassociation> implements ReptOptAssoDao {

	@Override
	public List<Reportoutputassociation> getListByReportId(long id) {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Reportoutputassociation.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("report.id", id));
		List<Reportoutputassociation> reportoutputassociations = criteria.list().size() > 0 ?  criteria.list(): null;
		return reportoutputassociations;
	}

}
