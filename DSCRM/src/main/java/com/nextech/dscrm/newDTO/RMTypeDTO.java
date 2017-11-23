package com.nextech.dscrm.newDTO;

import com.nextech.dscrm.dto.AbstractDTO;

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
