package com.nextech.erp.newDTO;

import com.nextech.erp.dto.AbstractDTO;

public class RMTypeDTO extends AbstractDTO{
	
	private String rmTypeName;
	
	public RMTypeDTO(){
		
	}
	public RMTypeDTO(int id){
		this.setId(id);
	}

	public String getRmTypeName() {
		return rmTypeName;
	}

	public void setRmTypeName(String rmTypeName) {
		this.rmTypeName = rmTypeName;
	}
}
