package com.nextech.erp.daoImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.StoreoutDao;
import com.nextech.erp.model.Storeout;

@Repository
@Transactional
public class StoreoutDaoImpl extends SuperDaoImpl<Storeout> implements StoreoutDao {

}
