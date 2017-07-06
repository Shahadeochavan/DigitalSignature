package com.nextech.erp.daoImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.ReportDao;
import com.nextech.erp.model.Report;

@Repository
@Transactional
public class ReportDaoImpl extends SuperDaoImpl<Report> implements ReportDao {

}
