package com.nextech.erp.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.ClientDao;
import com.nextech.erp.model.Client;

@Repository
@Transactional
public class ClientDaoImpl extends SuperDaoImpl<Client> implements ClientDao {
	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;
	@Override
	public Client getClientByCompanyName(String companyname) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Client.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("companyname", companyname));
		Client client = criteria.list().size() > 0 ? (Client) criteria.list()
				.get(0) : null;
		 // //session.close();
		return client;
	}

	@Override
	public Client getClientByEmail(String emailid) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Client.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("emailid", emailid));
		Client client = criteria.list().size() > 0 ? (Client) criteria.list()
				.get(0) : null;
		 // //session.close();
		return client;
	}
}
