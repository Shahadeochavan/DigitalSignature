package com.nextech.erp.daoImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.RawmaterialinventoryhistoryDao;
import com.nextech.erp.model.Rawmaterialinventoryhistory;

@Repository
@Transactional
public class RawmaterialinventoryhistoryDaoImpl extends SuperDaoImpl<Rawmaterialinventoryhistory> implements RawmaterialinventoryhistoryDao{

}
