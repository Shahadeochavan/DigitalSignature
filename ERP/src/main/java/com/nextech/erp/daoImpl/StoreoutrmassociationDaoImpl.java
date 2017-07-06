package com.nextech.erp.daoImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.StoreoutrmassociationDao;
import com.nextech.erp.model.Storeoutrmassociation;

@Repository
@Transactional
public class StoreoutrmassociationDaoImpl extends SuperDaoImpl<Storeoutrmassociation> implements StoreoutrmassociationDao{

}
