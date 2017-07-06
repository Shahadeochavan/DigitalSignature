package com.nextech.erp.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.SecurityCheckOutDTO;
import com.nextech.erp.dto.SecurityCheckOutPart;
import com.nextech.erp.model.Dispatch;
import com.nextech.erp.model.Productorderassociation;
import com.nextech.erp.model.Securitycheckout;
import com.nextech.erp.model.Status;
import com.nextech.erp.service.DispatchService;
import com.nextech.erp.service.ProductorderService;
import com.nextech.erp.service.ProductorderassociationService;
import com.nextech.erp.service.SecuritycheckoutService;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.status.UserStatus;


@Controller
@RequestMapping("/securitycheckout")
public class SecuritycheckoutController {

	@Autowired
	SecuritycheckoutService securitycheckoutService;

	@Autowired
	ProductorderService productorderService;


	@Autowired
	ProductorderassociationService productorderassociationService;

	@Autowired
	StatusService statusService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	DispatchService dispatchService;



	@RequestMapping(value = "/productOrderCheckOut", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addSecuritycheckout(@Valid @RequestBody SecurityCheckOutDTO securityCheckOutDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}

			Securitycheckout securitycheckout = new Securitycheckout();
			securitycheckout.setClientname(securityCheckOutDTO.getClientname());
			securitycheckout.setDescription(securityCheckOutDTO.getDescription());
			securitycheckout.setDriver_Name(securityCheckOutDTO.getDriver_Name());
			securitycheckout.setCreateDate(securityCheckOutDTO.getCreateDate());
			securitycheckout.setPoNo(securityCheckOutDTO.getPoNo());
			securitycheckout.setIntime(securityCheckOutDTO.getIntime());
			securitycheckout.setOuttime(securityCheckOutDTO.getOuttime());
			securitycheckout.setVehicleNo(securityCheckOutDTO.getVehicleNo());
			securitycheckout.setInvoice_No(securityCheckOutDTO.getInvoice_No());
			securitycheckout.setIsactive(true);
			securitycheckout.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			securitycheckout.setStatus(statusService.getEntityById(Status.class, Long.parseLong(messageSource.getMessage(ERPConstants.SECURITY_CHECK_COMPLETE, null, null))));
			securitycheckoutService.addEntity(securitycheckout);
			StringBuilder stringBuilder = new StringBuilder();
			String prefix="";
			String send="";
			for(SecurityCheckOutPart securityCheckOutPart : securityCheckOutDTO.getSecurityCheckOutParts()){
				Productorderassociation  productorderassociation = productorderassociationService.getProdcutAssoByOrder(securitycheckout.getPoNo());
				stringBuilder.append(prefix);
				prefix=",";
				stringBuilder.append(securityCheckOutPart.getProductId());
				send = stringBuilder.toString();
				securitycheckout.setDispatch(send);

				List<Dispatch> dispatchList = dispatchService.getDispatchByProductOrderId(securityCheckOutDTO.getPoNo());
				for(Dispatch dispatch : dispatchList){
					if(dispatch.getProduct().getId()==securityCheckOutPart.getProductId()){
				        dispatch.setIsactive(true);
				        dispatch.setStatus(statusService.getEntityById(Status.class, Long.parseLong(messageSource.getMessage(ERPConstants.ORDER_SECURITY_OUT, null, null))));
				        dispatch.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
				        dispatchService.updateEntity(dispatch);
					}
				}
			}
			securitycheckoutService.updateEntity(securitycheckout);


			return new UserStatus(1, "Securitycheckout added Successfully !");
		} catch (ConstraintViolationException cve) {
			System.out.println("Inside ConstraintViolationException");
			cve.printStackTrace();
			return new UserStatus(0, cve.getCause().getMessage());
		} catch (PersistenceException pe) {
			System.out.println("Inside PersistenceException");
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (Exception e) {
			System.out.println("Inside Exception");
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Securitycheckout getSecuritycheckout(@PathVariable("id") long id) {
		Securitycheckout securitycheckout = null;
		try {
			securitycheckout = securitycheckoutService.getEntityById(Securitycheckout.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return securitycheckout;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateSecuritycheckout(
			@RequestBody Securitycheckout securitycheckout,HttpServletRequest request,HttpServletResponse response) {
		try {
			securitycheckout.setIsactive(true);
			securitycheckout.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			securitycheckoutService.updateEntity(securitycheckout);
			return new UserStatus(1, "Securitycheckout update Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Securitycheckout> getSecuritycheckout() {

		List<Securitycheckout> securitycheckoutList = null;
		try {
			securitycheckoutList = securitycheckoutService.getEntityList(Securitycheckout.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return securitycheckoutList;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteSecuritycheckout(@PathVariable("id") long id) {

		try {
			Securitycheckout securitycheckout = securitycheckoutService.getEntityById(Securitycheckout.class,id);
			securitycheckout.setIsactive(false);
			securitycheckoutService.updateEntity(securitycheckout);
			return new UserStatus(1, "Securitycheckout deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}

}

