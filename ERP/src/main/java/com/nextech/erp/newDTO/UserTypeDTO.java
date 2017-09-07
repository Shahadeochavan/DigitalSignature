package com.nextech.erp.newDTO;

import com.nextech.erp.dto.AbstractDTO;

public class UserTypeDTO extends AbstractDTO{

	private String usertypeName;
	public UserTypeDTO(int id) {
		this.setId(id);
	}

	public UserTypeDTO() {
	}

	public String getUsertypeName() {
		return usertypeName;
	}

	public void setUsertypeName(String usertypeName) {
		this.usertypeName = usertypeName;
	}
	
}
