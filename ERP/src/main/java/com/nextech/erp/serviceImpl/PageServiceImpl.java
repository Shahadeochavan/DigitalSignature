package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.PageDao;
import com.nextech.erp.factory.PageFactory;
import com.nextech.erp.model.Page;
import com.nextech.erp.newDTO.PageDTO;
import com.nextech.erp.service.PageService;
@Service
public class PageServiceImpl extends CRUDServiceImpl<Page> implements PageService{

	@Autowired
	PageDao pageDao;
	@Override
	public Page getPageByUrl(String url) throws Exception {
		// TODO Auto-generated method stub
		return pageDao.getPageByUrl(url);
	}
	@Override
	public List<PageDTO> getPageDTOList(List<PageDTO> pageDTOs) throws Exception {
		// TODO Auto-generated method stub
		 pageDTOs = new ArrayList<PageDTO>();
		List<Page> pages = null;
		pages = pageDao.getList(Page.class);
		for (Page page : pages) {
			PageDTO pageDTO = PageFactory.setPageList(page);
			pageDTOs.add(pageDTO);
		}
		return pageDTOs;
	}
	@Override
	public PageDTO getPageDTOById(long id) throws Exception {
		// TODO Auto-generated method stub
		Page page = pageDao.getById(Page.class, id);
		PageDTO pageDTO = PageFactory.setPageList(page);
		return pageDTO;
	}
	@Override
	public void getPageById(long id) throws Exception {
		// TODO Auto-generated method stub
		Page page = pageDao.getById(Page.class, id);
		page.setIsactive(false);
		pageDao.update(page);
	}
	
}
