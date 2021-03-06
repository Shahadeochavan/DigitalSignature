package com.nextech.dscrm.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.dscrm.dao.PageDao;
import com.nextech.dscrm.factory.PageFactory;
import com.nextech.dscrm.model.Page;
import com.nextech.dscrm.newDTO.PageDTO;
import com.nextech.dscrm.service.PageService;
@Service
public class PageServiceImpl extends CRUDServiceImpl<Page> implements PageService{

	@Autowired
	PageDao pageDao;
	@Override
	public Page getPageByUrl(String url) throws Exception {
		
		return pageDao.getPageByUrl(url);
	}
	@Override
	public List<PageDTO> getPageDTOList(List<PageDTO> pageDTOs) throws Exception {
		 pageDTOs = new ArrayList<PageDTO>();
		List<Page> pages = null;
		pages = pageDao.getList(Page.class);
		if(pages.isEmpty()){
			return null;
		}
		for (Page page : pages) {
			PageDTO pageDTO = PageFactory.setPageList(page);
			pageDTOs.add(pageDTO);
		}
		return pageDTOs;
	}
	@Override
	public PageDTO getPageDTOById(long id) throws Exception {
		Page page = pageDao.getById(Page.class, id);
		if(page==null){
			return null;
		}
		PageDTO pageDTO = PageFactory.setPageList(page);
		return pageDTO;
	}
	@Override
	public PageDTO deletePageById(long id) throws Exception {
		Page page = pageDao.getById(Page.class, id);
		if(page==null){
			return null;
		}
		page.setIsactive(false);
		pageDao.update(page);
		PageDTO pageDTO = PageFactory.setPageList(page);
		return pageDTO;
	}
}
