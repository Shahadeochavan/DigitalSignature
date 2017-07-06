package com.nextech.erp.daoImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.RawmaterialorderhistoryDao;
import com.nextech.erp.model.Rawmaterialorderhistory;

@Repository
@Transactional
public class RawmaterialorderhistoryDaoImpl extends SuperDaoImpl<Rawmaterialorderhistory> implements RawmaterialorderhistoryDao{
	
}

