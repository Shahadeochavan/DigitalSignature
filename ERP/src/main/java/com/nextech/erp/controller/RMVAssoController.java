package com.nextech.erp.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.factory.RMVendorAssoRequestResponseFactory;
import com.nextech.erp.factory.TaxStructureRequestResponseFactory;
import com.nextech.erp.newDTO.RMVendorAssociationDTO;
import com.nextech.erp.newDTO.TaxStructureDTO;
import com.nextech.erp.service.RMVAssoService;
import com.nextech.erp.service.TaxstructureService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;

@Controller
@Transactional @RequestMapping("/rmvendorasso")
public class RMVAssoController {
	@Autowired
	RMVAssoService rmvAssoService;
	
	@Autowired
	TaxstructureService taxstructureService;
	@Autowired
	private MessageSource messageSource;

	@Transactional @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addRawMaterialVendorAssociation(
			@Valid @RequestBody RMVendorAssociationDTO rmVendorAssociationDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			if (rmvAssoService.getRMVAssoByVendorIdRMId(
					rmVendorAssociationDTO.getVendorId().getId(),
					rmVendorAssociationDTO.getRawmaterialId().getId()) == null){
				long id=	taxstructureService.addEntity(TaxStructureRequestResponseFactory.setTaxStructure(rmVendorAssociationDTO.getTaxStructureDTO(), request));
				TaxStructureDTO  taxStructureDTO =  new TaxStructureDTO();
				taxStructureDTO.setId(id);
				rmVendorAssociationDTO.setTaxStructureDTO(taxStructureDTO);
			
				rmvAssoService.addEntity(RMVendorAssoRequestResponseFactory.setRMVendorAsso(rmVendorAssociationDTO, request));
			}	
			else
				return new UserStatus(2, messageSource.getMessage(
						ERPConstants.VENDOR_RM_ASSO_EXIT, null, null));
			return new UserStatus(1,
					"Rawmaterialvendorassociation added Successfully !");
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

	@Transactional @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getRawMaterialVendorAssociation(
			@PathVariable("id") long id) {
		RMVendorAssociationDTO rawmaterialvendorassociation = null;
		try {
			rawmaterialvendorassociation = rmvAssoService.getRMVendor(id);
			if(rawmaterialvendorassociation==null){
				return  new Response(1,"There is no any rm vendor association");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,rawmaterialvendorassociation);
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateRawMaterialVendorAssociation(
			@RequestBody RMVendorAssociationDTO rmVendorAssociationDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			rmvAssoService.updateEntity(RMVendorAssoRequestResponseFactory.setRMVendorAssoUpdate(rmVendorAssociationDTO, request));
			return new UserStatus(1,
					"Rawmaterialvendorassociation update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getRawMaterialVendorAssociation() {

		List<RMVendorAssociationDTO> rawmaterialvendorassociationList = null;
		try {
			rawmaterialvendorassociationList = rmvAssoService.getRMVendorList();
			if(rawmaterialvendorassociationList==null){
				return  new Response(1,"There is no rm vendor association");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response(1,rawmaterialvendorassociationList);
	}
	
	@Transactional @RequestMapping(value = "rmVendorList/{rmId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getRMVendorList(@PathVariable("rmId") long rmId) {
		List<RMVendorAssociationDTO> rmRawmaterialvendorassociations = null;
		try {
			rmRawmaterialvendorassociations	 = rmvAssoService.getRawmaterialvendorassociationListByRMId(rmId);
			if(rmRawmaterialvendorassociations==null){
				return new Response(1,"There is no any rm vendor association");
			}
			
		} catch (Exception e) {
			
		}
		return new Response(1,rmRawmaterialvendorassociations);
	}
	
	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteRawMaterialVendorAssociation(
			@PathVariable("id") long id) {

		try {
			RMVendorAssociationDTO rmVendorAssociationDTO = rmvAssoService.deleteRMVendor(id);
			if(rmVendorAssociationDTO==null){
				return new Response(1,"There is no rm vendor association");
			}
			return new Response(1,
					"Rawmaterialvendorassociation deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}
	}
}