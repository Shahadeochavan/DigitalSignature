package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.Taxstructure;
import com.nextech.erp.newDTO.TaxStructureDTO;

public class TaxStructureRequestResponseFactory {
	
	public static Taxstructure setTaxStructure(TaxStructureDTO taxStructureDTO,HttpServletRequest request){
		Taxstructure taxstructure =  new Taxstructure();
		taxstructure.setCgst(taxStructureDTO.getCgst());
		taxstructure.setIgst(taxStructureDTO.getIgst());
		taxstructure.setIsactive(true);
		taxstructure.setOther1(taxStructureDTO.getCgst());
		taxstructure.setOther2(taxStructureDTO.getOther2());
		taxstructure.setSgst(taxStructureDTO.getSgst());
		taxstructure.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return taxstructure;
	}

}
