package com.nextech.dscrm.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.nextech.dscrm.constants.ERPConstants;
import com.nextech.dscrm.dao.DispatchDao;
import com.nextech.dscrm.dao.ProductorderassociationDao;
import com.nextech.dscrm.dao.SecuritycheckoutDao;
import com.nextech.dscrm.dao.StatusDao;
import com.nextech.dscrm.dto.SecurityCheckOutDTO;
import com.nextech.dscrm.dto.SecurityCheckOutPart;
import com.nextech.dscrm.factory.SecurityCheckOutRequestResponseFactory;
import com.nextech.dscrm.model.Dispatch;
import com.nextech.dscrm.model.Productorderassociation;
import com.nextech.dscrm.model.Securitycheckout;
import com.nextech.dscrm.model.Status;
import com.nextech.dscrm.service.SecuritycheckoutService;
@Service
public class SecuritycheckoutServiceImpl extends CRUDServiceImpl<Securitycheckout> implements SecuritycheckoutService{

	@Autowired
	SecuritycheckoutDao securitycheckoutDao;
	
	@Autowired
	StatusDao statusDao;
	
	@Autowired
	DispatchDao dispatchDao;
	
	@Autowired
	ProductorderassociationDao productorderassociationDao;
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public SecurityCheckOutDTO addSecurityCheckOut(SecurityCheckOutDTO securityCheckOutDTO, HttpServletRequest request)throws Exception {
		
		Securitycheckout securitycheckout = SecurityCheckOutRequestResponseFactory.setSecurityCheckOut(securityCheckOutDTO, request);
		securitycheckout.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		securitycheckout.setStatus(statusDao.getById(Status.class, Long.parseLong(messageSource.getMessage(ERPConstants.SECURITY_CHECK_COMPLETE, null, null))));
		securitycheckoutDao.add(securitycheckout);
		StringBuilder stringBuilder = new StringBuilder();
		String prefix="";
		String send="";
		for(SecurityCheckOutPart securityCheckOutPart : securityCheckOutDTO.getSecurityCheckOutParts()){
			Productorderassociation  productorderassociation = productorderassociationDao.getProdcutAssoByOrder(securitycheckout.getPoNo());
			stringBuilder.append(prefix);
			prefix=",";
			stringBuilder.append(securityCheckOutPart.getProductId());
			send = stringBuilder.toString();
			securitycheckout.setDispatch(send);

			List<Dispatch> dispatchList = dispatchDao.getDispatchByProductOrderId(securityCheckOutDTO.getPoNo());
			for(Dispatch dispatch : dispatchList){
				if(dispatch.getProduct().getId()==securityCheckOutPart.getProductId()){
			        dispatch.setIsactive(true);
			        dispatch.setStatus(statusDao.getById(Status.class, Long.parseLong(messageSource.getMessage(ERPConstants.ORDER_SECURITY_OUT, null, null))));
			        dispatch.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			        dispatchDao.update(dispatch);
				}
			}
		}
		securitycheckoutDao.update(securitycheckout);
		return null;
	}

	@Override
	public SecurityCheckOutDTO getSecurityCheckOutById(long id)
			throws Exception {
		
		Securitycheckout securitycheckout = securitycheckoutDao.getById(Securitycheckout.class, id);
		if(securitycheckout==null){
			return null;
		}
		SecurityCheckOutDTO securityCheckOutDTO = SecurityCheckOutRequestResponseFactory.setSecurityCheckOutDTO(securitycheckout);
		return securityCheckOutDTO;
	}

	@Override
	public List<SecurityCheckOutDTO> getSecurityCheckOutList() throws Exception {
		
		List<SecurityCheckOutDTO> securityCheckOutDTOs =  new ArrayList<SecurityCheckOutDTO>();
		List<Securitycheckout> securitycheckouts = securitycheckoutDao.getList(Securitycheckout.class);
		if(securitycheckouts.isEmpty()){
			return null;
		}
		for (Securitycheckout securitycheckout : securitycheckouts) {
			SecurityCheckOutDTO securityCheckOutDTO = SecurityCheckOutRequestResponseFactory.setSecurityCheckOutDTO(securitycheckout);
			securityCheckOutDTOs.add(securityCheckOutDTO);
		}
		return securityCheckOutDTOs;
	}

	@Override
	public SecurityCheckOutDTO deleteSecurityCheckOut(long id) throws Exception {
		
		Securitycheckout securitycheckout = securitycheckoutDao.getById(Securitycheckout.class, id);
		if(securitycheckout==null){
			return null;
		}
		securitycheckout.setIsactive(false);
		securitycheckoutDao.update(securitycheckout);
		SecurityCheckOutDTO securityCheckOutDTO = SecurityCheckOutRequestResponseFactory.setSecurityCheckOutDTO(securitycheckout);
		return securityCheckOutDTO;
	}

}
