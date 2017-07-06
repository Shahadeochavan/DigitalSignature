package com.nextech.erp.daoImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.ReptInpParaDao;
import com.nextech.erp.model.Reportinputparameter;

@Repository
@Transactional
public class ReptInpParaDaoImpl extends SuperDaoImpl<Reportinputparameter> implements ReptInpParaDao {

}
