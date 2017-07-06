package com.nextech.erp.daoImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.UnitDao;
import com.nextech.erp.model.Unit;

@Repository
@Transactional
public class UnitDaoImpl extends SuperDaoImpl<Unit> implements UnitDao {
}
