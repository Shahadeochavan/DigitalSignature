package com.nextech.erp.daoImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.ReptOptParaDao;
import com.nextech.erp.model.Reportoutputparameter;

@Repository
@Transactional
public class ReptOptParaDaoImpl extends SuperDaoImpl<Reportoutputparameter> implements ReptOptParaDao {

}
