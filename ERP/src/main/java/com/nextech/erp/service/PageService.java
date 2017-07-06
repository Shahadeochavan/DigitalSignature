package com.nextech.erp.service;

import com.nextech.erp.model.Page;

public interface PageService extends CRUDService<Page>{
	
	public Page getPageByUrl(String url) throws Exception;
	
}
