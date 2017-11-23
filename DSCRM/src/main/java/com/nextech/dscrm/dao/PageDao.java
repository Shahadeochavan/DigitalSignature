package com.nextech.dscrm.dao;

import com.nextech.dscrm.model.Page;

public interface PageDao extends SuperDao<Page>{
	
	public Page getPageByUrl(String url) throws Exception;
}
