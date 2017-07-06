package com.nextech.erp.daoImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.StoreoutrmDao;
import com.nextech.erp.model.Storeoutrm;

@Repository
@Transactional
public class StoreoutrmDaoImpl extends SuperDaoImpl<Storeoutrm> implements StoreoutrmDao{

}
