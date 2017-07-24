package com.nextech.erp.newDTO;



import java.util.List;

import com.nextech.erp.dto.AbstractDTO;
import com.nextech.erp.model.Page;
import com.nextech.erp.model.Usertype;

public class UserTypePageAssoDTO extends AbstractDTO{

	private Page page;
	private Usertype usertypeId;
	
	private List<UserTypePageAssoPart> userTypePageAssoParts;

	public Usertype getUsertypeId() {
		return usertypeId;
	}

	public void setUsertypeId(Usertype usertypeId) {
		this.usertypeId = usertypeId;
	}

	public List<UserTypePageAssoPart> getUserTypePageAssoParts() {
		return userTypePageAssoParts;
	}

	public void setUserTypePageAssoParts(
			List<UserTypePageAssoPart> userTypePageAssoParts) {
		this.userTypePageAssoParts = userTypePageAssoParts;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	
}
