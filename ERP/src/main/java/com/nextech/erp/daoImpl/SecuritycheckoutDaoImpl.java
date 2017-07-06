package com.nextech.erp.daoImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.SecuritycheckoutDao;
import com.nextech.erp.model.Securitycheckout;

@Repository
@Transactional
public class SecuritycheckoutDaoImpl extends SuperDaoImpl<Securitycheckout> implements SecuritycheckoutDao{

}
