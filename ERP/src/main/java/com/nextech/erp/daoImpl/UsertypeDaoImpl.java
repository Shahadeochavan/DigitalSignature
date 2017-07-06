package com.nextech.erp.daoImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.UserTypeDao;
import com.nextech.erp.model.Usertype;

@Repository
@Transactional
public class UsertypeDaoImpl extends SuperDaoImpl<Usertype> implements UserTypeDao {
	
}

