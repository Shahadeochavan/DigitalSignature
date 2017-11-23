package com.nextech.dscrm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.dscrm.dao.ClientproductassoDao;
import com.nextech.dscrm.model.Clientproductasso;
import com.nextech.dscrm.service.ClientproductassoService;

@Service
public class ClientproductassoServiceImpl extends CRUDServiceImpl<Clientproductasso> implements ClientproductassoService{

	@Autowired
	ClientproductassoDao clientproductassoDao;
	@Override
	public List<Clientproductasso> getClientProductAssoListByClientId(
			long clientId) throws Exception {
		// TODO Auto-generated method stub
		return clientproductassoDao.getClientProductAssoListByClientId(clientId);
	}

}
