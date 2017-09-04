package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dao.RawmaterialorderDao;
import com.nextech.erp.dao.StatusDao;
import com.nextech.erp.dto.RawmaterialOrderDTO;
import com.nextech.erp.factory.RMOrderRequestResponseFactory;
import com.nextech.erp.model.Rawmaterialorder;
import com.nextech.erp.model.Status;
import com.nextech.erp.service.RawmaterialorderService;
@Service
public class RawmaterialorderServiceImpl extends CRUDServiceImpl<Rawmaterialorder> implements RawmaterialorderService{

	@Autowired
	RawmaterialorderDao rawmaterialorderDao;
	
	@Autowired 
	StatusDao StatusDao;
	
	@Autowired
	private MessageSource messageSource;

	@Override
	public Rawmaterialorder getRawmaterialorderByIdName(long id,String rmname) throws Exception {
		return rawmaterialorderDao.getRawmaterialorderByIdName(id,rmname);
	}

	@Override
	public List<RawmaterialOrderDTO> getRawmaterialorderByStatusId(long statusId,long statusId1,long statusId2)
			throws Exception {
		
		List<RawmaterialOrderDTO> rawmaterialOrderDTOs = new ArrayList<RawmaterialOrderDTO>();
		List<Rawmaterialorder> rawmaterialorders = rawmaterialorderDao.getRawmaterialorderByStatusId(statusId,statusId1,statusId2);
		if(rawmaterialorders==null){
			return null;
		}
		for (Rawmaterialorder rawmaterialorder : rawmaterialorders) {
			RawmaterialOrderDTO rawmaterialOrderDTO = RMOrderRequestResponseFactory.setRMOrderDTO(rawmaterialorder);
			rawmaterialOrderDTOs.add(rawmaterialOrderDTO);
		}
		return rawmaterialOrderDTOs;
	}

	@Override
	public List<RawmaterialOrderDTO> getRawmaterialorderByQualityCheckStatusId(
			long statusId) throws Exception {
		
		List<RawmaterialOrderDTO> rawmaterialOrderDTOs = new ArrayList<RawmaterialOrderDTO>();
		List<Rawmaterialorder> rawmaterialorders = rawmaterialorderDao.getRawmaterialorderByQualityCheckStatusId(statusId);
		if(rawmaterialorders.isEmpty()){
			return null;
		}
		for (Rawmaterialorder rawmaterialorder : rawmaterialorders) {
			RawmaterialOrderDTO rawmaterialOrderDTO = RMOrderRequestResponseFactory.setRMOrderDTO(rawmaterialorder);
			rawmaterialOrderDTOs.add(rawmaterialOrderDTO);
		}
		return rawmaterialOrderDTOs;
	}

	@Override
	public List<RawmaterialOrderDTO> getRawmaterialorderByVendor(long vendorId)
			throws Exception {
		
		List<RawmaterialOrderDTO> rawmaterialOrderDTOs =  new ArrayList<RawmaterialOrderDTO>();
		List<Rawmaterialorder> rawmaterialorders = rawmaterialorderDao.getRawmaterialorderByVendor(vendorId);
		if(rawmaterialorders.isEmpty()){
			return null; 
		}
		for (Rawmaterialorder rawmaterialorder : rawmaterialorders) {
			RawmaterialOrderDTO rawmaterialOrderDTO = RMOrderRequestResponseFactory.setRMOrderDTO(rawmaterialorder);
			rawmaterialOrderDTOs.add(rawmaterialOrderDTO);
		}
		return rawmaterialOrderDTOs;
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialByName(String name)
			throws Exception {
		
		return rawmaterialorderDao.getRawmaterialByName(name);
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialorderByVendorId(long vendorId,
			long statusId1, long statusId2) throws Exception {
		
		return null;
	}

	@Override
	public RawmaterialOrderDTO addMultipleRawMaterialOrder(RawmaterialOrderDTO rawmaterialOrderDTO,HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		Rawmaterialorder  rawmaterialorder = RMOrderRequestResponseFactory.setRMOrder(rawmaterialOrderDTO);
		rawmaterialorder.setStatus(StatusDao.getById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_NEW_RM_ORDER, null, null))));
		rawmaterialorder.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		long id=rawmaterialorderDao.add(rawmaterialorder);
		String invoiceId = generateInvoiceId()+rawmaterialorder.getId();
		rawmaterialorder.setName(invoiceId);
		rawmaterialorderDao.update(rawmaterialorder);
		RawmaterialOrderDTO rawmaterialOrderDTO2 =  new RawmaterialOrderDTO();
		rawmaterialOrderDTO2.setId(rawmaterialorder.getId());
		rawmaterialOrderDTO2.setStatusId(rawmaterialorder.getStatus());
		return rawmaterialOrderDTO2;
	}

	@Override
	public List<RawmaterialOrderDTO> getRMOrderList() throws Exception {
		
		List<RawmaterialOrderDTO> rawmaterialOrderDTOs = new ArrayList<RawmaterialOrderDTO>();
		List<Rawmaterialorder> rawmaterialorders = rawmaterialorderDao.getList(Rawmaterialorder.class);
		if(rawmaterialorders.isEmpty()){
			return null;
		}
		for (Rawmaterialorder rawmaterialorder : rawmaterialorders) {
			RawmaterialOrderDTO  rawmaterialOrderDTO = RMOrderRequestResponseFactory.setRMOrderDTO(rawmaterialorder);
			rawmaterialOrderDTOs.add(rawmaterialOrderDTO);
		}
		return rawmaterialOrderDTOs;
	}

	@Override
	public RawmaterialOrderDTO getRMOrderById(long id) throws Exception {
		
		Rawmaterialorder rawmaterialorder = rawmaterialorderDao.getById(Rawmaterialorder.class, id);
		if(rawmaterialorder==null){
			return null;
		}
		RawmaterialOrderDTO  rawmaterialOrderDTO = RMOrderRequestResponseFactory.setRMOrderDTO(rawmaterialorder);
		return rawmaterialOrderDTO;
	}

	@Override
	public RawmaterialOrderDTO deleteRMOrder(long id) throws Exception {
		
		Rawmaterialorder rawmaterialorder = rawmaterialorderDao.getById(Rawmaterialorder.class, id);
		if(rawmaterialorder==null){
			return null;
		}
		rawmaterialorder.setIsactive(false);
		rawmaterialorderDao.update(rawmaterialorder);
		RawmaterialOrderDTO  rawmaterialOrderDTO = RMOrderRequestResponseFactory.setRMOrderDTO(rawmaterialorder);
		return rawmaterialOrderDTO;
	}
	private String generateInvoiceId(){
		String year="";
		Date currentDate = new Date();
		if(currentDate.getMonth()+1 > 3){
			int str = currentDate.getYear()+1900;
			int stri = str + 1;
			String strDate = stri+"";
			year = str+"/"+strDate.substring(2);
		}else{
			int str = currentDate.getYear()+1899;
			int stri = str + 1;
			String strDate = stri+"";
			year = str+"/"+strDate.substring(2);
		}
		year = "EK/PUN/"+year+"/";
		return year;
	}

	@Override
	public RawmaterialOrderDTO updateRMOrder(RawmaterialOrderDTO rawmaterialOrderDTO,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Rawmaterialorder  rawmaterialorder = RMOrderRequestResponseFactory.setRMOrder(rawmaterialOrderDTO);
		rawmaterialorder.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		rawmaterialorder.setStatus(StatusDao.getById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_NEW_RM_ORDER, null, null))));
		rawmaterialorderDao.update(rawmaterialorder);
		return rawmaterialOrderDTO;
	}
}

