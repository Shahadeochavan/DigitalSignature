package com.nextech.dscrm.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.dscrm.dto.BankdetailDTO;
import com.nextech.dscrm.model.Bankdetail;

public class BankdetailRequestResponseFactory {

	public static Bankdetail setBankdetail(BankdetailDTO bankdetailDTO,HttpServletRequest request){
		Bankdetail  bankdetail =  new Bankdetail();
		bankdetail.setAccountNumber(bankdetailDTO.getAccountNumber());
		bankdetail.setBankName(bankdetailDTO.getBankName());
		bankdetail.setBranchName(bankdetailDTO.getBranchName());
		bankdetail.setIfsc(bankdetailDTO.getIfscCoe());
		bankdetail.setDescription(bankdetailDTO.getDescription());
		bankdetail.setIsactive(true);
		bankdetail.setCreatedBy(Integer.valueOf(request.getAttribute("current_user").toString()));
		return bankdetail;
	}
	
	public static Bankdetail setBankdetailUpdate(BankdetailDTO bankdetailDTO,HttpServletRequest request){
		Bankdetail  bankdetail =  new Bankdetail();
		bankdetail.setAccountNumber(bankdetailDTO.getAccountNumber());
		bankdetail.setBankName(bankdetailDTO.getBankName());
		bankdetail.setBranchName(bankdetailDTO.getBranchName());
		bankdetail.setIfsc(bankdetailDTO.getIfscCoe());
		bankdetail.setDescription(bankdetailDTO.getDescription());
		bankdetail.setId(bankdetailDTO.getId());
		bankdetail.setIsactive(true);
		bankdetail.setUpdatedBy(Integer.valueOf(request.getAttribute("current_user").toString()));
		return bankdetail;
	}

}
