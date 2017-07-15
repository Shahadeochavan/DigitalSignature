package com.nextech.erp.newDTO;

import com.nextech.erp.dto.AbstractDTO;




public class PageDTO extends AbstractDTO{

	private String menu;
	private String pageName;
	private String submenu;
	private String url;

	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getSubmenu() {
		return submenu;
	}
	public void setSubmenu(String submenu) {
		this.submenu = submenu;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
