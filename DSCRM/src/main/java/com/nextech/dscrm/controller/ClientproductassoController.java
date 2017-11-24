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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.dscrm.dto.ClientproductassoDTO;
import com.nextech.dscrm.factory.ClientProductAssoRequestResponseFactory;
import com.nextech.dscrm.model.Clientproductasso;
import com.nextech.dscrm.model.User;
import com.nextech.dscrm.service.ClientproductassoService;
import com.nextech.dscrm.service.UserService;
import com.nextech.dscrm.status.Response;
import com.nextech.dscrm.status.UserStatus;

@Controller
@Transactional @RequestMapping("/clientProducAsso")
public class ClientproductassoController {
	
	@Autowired 
	ClientproductassoService clientproductassoService;
	
    static Logger logger = Logger.getLogger(BankdetailController.class);
    
    @Autowired
	UserService userService;
    
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addBankdetail(@Valid @RequestBody ClientproductassoDTO clientproductassoDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			
			clientproductassoService.addEntity(ClientProductAssoRequestResponseFactory.setClientProductAsso(clientproductassoDTO, request));
			return new UserStatus(1, "Client Product Aassociation added Successfully !");
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
	public @ResponseBody Response getClientproductasso(@PathVariable("id") long id) {
		Clientproductasso clientproductasso = null;
		try {
			clientproductasso = clientproductassoService.getEntityById(Clientproductasso.class, id);
			if(clientproductasso==null){
				return new Response(1,"There is no any clientproductasso");
			}
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,clientproductasso);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateBankdetail(@RequestBody ClientproductassoDTO clientproductassoDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			
			clientproductassoService.updateEntity(ClientProductAssoRequestResponseFactory.setClientProductAssoUpdate(clientproductassoDTO, request));
			return new UserStatus(1, "Clientproductasso update Successfully !");
		} catch (Exception e) {
			logger.error(e);
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getClientproductasso(HttpServletRequest request) {

		List<Clientproductasso> clientproductassos = null;
		try {
			clientproductassos = clientproductassoService.getEntityList(Clientproductasso.class);
			if(clientproductassos==null){
				 logger.info("there is no any Clientproductasso list");
				return new Response(1,"There is no any Clientproductasso list");
			}
			
			User user =  new User();
			user.setId(Long.parseLong(request.getAttribute("current_user").toString()));
			System.out.println("User id is"+user.getId());
			User user2 =  userService.getEntityById(User.class, user.getId());
			if(user2.getUsertype().getId()==11){
				clientproductassos = clientproductassoService.getClientProductAssoListByClientId(user2.getClientId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response(1,clientproductassos);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteClientproductasso(@PathVariable("id") long id) {

		try {
			Clientproductasso clientproductasso =clientproductassoService.getEntityById(Clientproductasso.class, id);
			if (clientproductasso==null) {
				 logger.info("there is no any clientproductasso");
				return new Response(1,"there is no any clientproductasso");
			}
			clientproductasso.setIsactive(false);
			clientproductassoService.updateEntity(clientproductasso);
			return new Response(1, "clientproductasso deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}

	}
	
	@RequestMapping(value = "ProductList/{clientId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getClientProductList(@PathVariable("clientId") long clientId) {
		List<Clientproductasso>  clientproductassos =null;
		try {
			clientproductassos = clientproductassoService.getClientProductAssoListByClientId(clientId);
			if (clientproductassos==null) {
				 logger.info("there is no any clientproductasso");
				return new Response(1,"there is no any clientproductasso");
			}
		} catch (Exception e) {
			return new Response(0, e.toString());
		}
		return new Response(1,clientproductassos);

	}
}
