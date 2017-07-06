package com.nextech.erp.dao;

import com.nextech.erp.model.Page;

public interface PageDao extends SuperDao<Page>{
	
	public Page getPageByUrl(String url) throws Exception;
}
