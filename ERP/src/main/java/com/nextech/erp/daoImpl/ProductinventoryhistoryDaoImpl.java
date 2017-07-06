package com.nextech.erp.daoImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.ProductinventoryhistoryDao;
import com.nextech.erp.model.Productinventoryhistory;

@Repository
@Transactional
public class ProductinventoryhistoryDaoImpl extends SuperDaoImpl<Productinventoryhistory>
		implements ProductinventoryhistoryDao {

}
