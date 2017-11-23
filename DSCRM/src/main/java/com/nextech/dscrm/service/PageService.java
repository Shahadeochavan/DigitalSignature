package com.nextech.dscrm.service;

import java.util.List;

import com.nextech.dscrm.model.Page;
import com.nextech.dscrm.newDTO.PageDTO;

public interface PageService extends CRUDService<Page>{
	
	public Page getPageByUrl(String url) throws Exception;
	
	public List<PageDTO>  getPageDTOList(List<PageDTO> pageDTOs) throws Exception;
	
	public PageDTO getPageDTOById(long id) throws Exception;
	
	public PageDTO deletePageById(long id) throws Exception;
	
}
