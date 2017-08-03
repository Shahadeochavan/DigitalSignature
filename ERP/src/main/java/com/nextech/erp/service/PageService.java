package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Page;
import com.nextech.erp.newDTO.PageDTO;

public interface PageService extends CRUDService<Page>{
	
	public Page getPageByUrl(String url) throws Exception;
	
	public List<PageDTO>  getPageDTOList(List<PageDTO> pageDTOs) throws Exception;
	
	public PageDTO getPageDTOById(long id) throws Exception;
	
	public void deletePageById(long id) throws Exception;
	
}
