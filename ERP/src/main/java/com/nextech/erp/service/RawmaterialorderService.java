package com.nextech.erp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.RawmaterialOrderDTO;
import com.nextech.erp.factory.RMOrderRequestResponseFactory;
import com.nextech.erp.model.Rawmaterialorder;
import com.nextech.erp.model.Status;

public interface RawmaterialorderService extends CRUDService<Rawmaterialorder> {

	public Rawmaterialorder getRawmaterialorderByIdName(long id ,String rmname) throws Exception;

	public List<Rawmaterialorder> getRawmaterialorderByStatusId(long statusId,long statusId1,long statusId2) throws Exception;

	public List<Rawmaterialorder> getRawmaterialorderByQualityCheckStatusId(long statusId) throws Exception;

	public List<Rawmaterialorder> getRawmaterialorderByVendor(long  vendorId)throws Exception;

	public List<Rawmaterialorder> getRawmaterialByName(String  name)throws Exception;
	
	public List<Rawmaterialorder> getRawmaterialorderByVendorId(long vendorId,long statusId1,long statusId2) throws Exception;
	
	public RawmaterialOrderDTO  saveRMOrder(RawmaterialOrderDTO rawmaterialOrderDTO,HttpServletRequest request,HttpServletResponse response)throws Exception;
	
	public void updateRMName(String name,RawmaterialOrderDTO rawmaterialOrderDTO) throws Exception;
	
	public List<RawmaterialOrderDTO> getRMOrderList() throws Exception;
	
	public RawmaterialOrderDTO getRMOrderById(long id) throws Exception;
	
	public RawmaterialOrderDTO deleteRMOrder(long id) throws Exception;
	
} 

