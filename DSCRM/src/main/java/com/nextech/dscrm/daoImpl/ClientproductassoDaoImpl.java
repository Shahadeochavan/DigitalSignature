package com.nextech.dscrm.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.nextech.dscrm.dao.ClientproductassoDao;
import com.nextech.dscrm.model.Clientproductasso;

@Repository
public class ClientproductassoDaoImpl extends SuperDaoImpl<Clientproductasso> implements ClientproductassoDao{

	@Override
	public List<Clientproductasso> getClientProductAssoListByClientId(
			long clientId) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Clientproductasso> criteria = builder.createQuery(Clientproductasso.class);
		Root<Clientproductasso> userRoot  = (Root<Clientproductasso>) criteria.from(Clientproductasso.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("client"), clientId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Clientproductasso> query = session.createQuery(criteria);
		return query.getResultList();
	}

}
