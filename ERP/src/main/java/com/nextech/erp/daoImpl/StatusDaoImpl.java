package com.nextech.erp.daoImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.StatusDao;
import com.nextech.erp.model.Status;

@Repository
@Transactional
public class StatusDaoImpl extends SuperDaoImpl<Status> implements StatusDao{

}