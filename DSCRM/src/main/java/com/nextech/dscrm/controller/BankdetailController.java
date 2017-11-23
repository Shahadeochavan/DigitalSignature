package com.nextech.dscrm.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.dscrm.dto.BankdetailDTO;
import com.nextech.dscrm.factory.BankdetailRequestResponseFactory;
import com.nextech.dscrm.model.Bankdetail;
import com.nextech.dscrm.service.BankdetailService;
import com.nextech.dscrm.status.Response;
import com.nextech.dscrm.status.UserStatus;


@Controller
@RequestMapping("/bankdetail")
public class BankdetailController {

	@Autowired 
	BankdetailService bankdetailService;
    static Logger logger = Logger.getLogger(BankdetailController.class);
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addBankdetail(@Valid @RequestBody BankdetailDTO bankdetailDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			
			bankdetailService.addEntity(BankdetailRequestResponseFactory.setBankdetail(bankdetailDTO, request));
			return new UserStatus(1, "Bankdetail added Successfully !");
		} catch (ConstraintViolationException cve) {
			logger.error(cve);
			cve.printStackTrace();
			return new UserStatus(0, cve.getCause().getMessage());
		} catch (PersistenceException pe) {
			logger.error(pe);
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getBankdetail(@PathVariable("id") long id) {
		Bankdetail bankdetail = null;
		try {
			bankdetail = bankdetailService.getEntityById(Bankdetail.class, id);
			if(bankdetail==null){
				return new Response(1,"There is no any Bankdetail");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,bankdetail);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateBankdetail(@RequestBody BankdetailDTO bankdetailDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			
			bankdetailService.updateEntity(BankdetailRequestResponseFactory.setBankdetailUpdate(bankdetailDTO, request));
			return new UserStatus(1, "Bankdetail update Successfully !");
		} catch (Exception e) {
			logger.error(e);
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getBankdetail() {

		List<Bankdetail> bankdetails = null;
		try {
			bankdetails = bankdetailService.getEntityList(Bankdetail.class);
			if(bankdetails==null){
				 logger.info("there is no any Bankdetail list");
				return new Response(1,"There is no any Bankdetail list");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response(1,bankdetails);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteBankdetail(@PathVariable("id") long id) {

		try {
			Bankdetail bankdetail =bankdetailService.getEntityById(Bankdetail.class, id);
			if (bankdetail==null) {
				 logger.info("there is no any Bankdetail");
				return new Response(1,"there is no any Bankdetail");
			}
			bankdetail.setIsactive(false);
			bankdetailService.updateEntity(bankdetail);
			return new Response(1, "Bankdetail deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}

	}
}


