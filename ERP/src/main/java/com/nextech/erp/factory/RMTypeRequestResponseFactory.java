package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.Rmtype;
import com.nextech.erp.newDTO.RMTypeDTO;

public class RMTypeRequestResponseFactory {
	
	public static Rmtype setRMType(RMTypeDTO rawRmTypeDTO,HttpServletRequest request){
		Rmtype rmtype = new Rmtype();
		rmtype.setName(rawRmTypeDTO.getRmTypeName());
		rmtype.setDescription(rawRmTypeDTO.getDescription());
		rmtype.setId(rawRmTypeDTO.getId());
		rmtype.setIsactive(true);
		rmtype.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return rmtype;
	}
	
	public static Rmtype setRMTypeUpdate(RMTypeDTO rawRmTypeDTO,HttpServletRequest request){
		Rmtype rmtype = new Rmtype();
		rmtype.setName(rawRmTypeDTO.getRmTypeName());
		rmtype.setDescription(rawRmTypeDTO.getDescription());
		rmtype.setId(rawRmTypeDTO.getId());
		rmtype.setIsactive(true);
		rmtype.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return rmtype;
	}
	
	public static RMTypeDTO setRMTypeDTO(Rmtype rmtype){
		RMTypeDTO rmTypeDTO =  new  RMTypeDTO();
		rmTypeDTO.setActive(true);
		rmTypeDTO.setDescription(rmtype.getDescription());
		rmTypeDTO.setRmTypeName(rmtype.getName());
		rmTypeDTO.setId(rmtype.getId());
		return rmTypeDTO;
	} 
 
}
