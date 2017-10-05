package com.nextech.erp.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
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
import com.nextech.erp.factory.PageFactory;
import com.nextech.erp.newDTO.PageDTO;
import com.nextech.erp.service.PageService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;

@Controller
@RequestMapping("/page")
public class PageController {

	@Autowired
	PageService pageservice;
	@Transactional @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addPage(@Valid @RequestBody PageDTO pageDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			pageservice.addEntity(PageFactory.setPage(pageDTO, request));
			return new UserStatus(1, "Page added Successfully !");
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
	public @ResponseBody Response getPage(@PathVariable("id") long id) {
		PageDTO pageDTO = null;
		try {
			pageDTO = pageservice.getPageDTOById(id);
			if(pageDTO==null){
				return new Response(1,"There is no any page");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,pageDTO);
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updatePage(@RequestBody PageDTO pageDTO,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			pageservice.updateEntity(PageFactory.setPageUpdate(pageDTO, request));
			return new UserStatus(1, "Page update Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getPageList() {

		List<PageDTO> pageList = null;
		try {
			pageList = pageservice.getPageDTOList(pageList);
			if(pageList==null){
				return new Response(1,"There is no any page list");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response(1,pageList);
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deletePage(@PathVariable("id") long id) {

		try {
			PageDTO pageDTO= pageservice.deletePageById(id);
			if(pageDTO==null){
				return  new Response(1,"There is no any page");
			}
			return new Response(1, "Page deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}

	}
}
