package com.nextech.dscrm.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nextech.dscrm.dto.ApplicantDTO;
import com.nextech.dscrm.factory.ApplicantRequestResponseFactory;
import com.nextech.dscrm.model.Applicant;
import com.nextech.dscrm.model.Client;
import com.nextech.dscrm.model.Product;
import com.nextech.dscrm.service.ApplicantService;
import com.nextech.dscrm.status.Response;
import com.nextech.dscrm.status.UserStatus;

@Controller
@Transactional @RequestMapping("/applicant")
public class ApplicantController {
	@Autowired
	ApplicantService applicantService;
	
	static Logger logger = Logger.getLogger(ApplicantController.class);
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addApplicant(@Valid @RequestBody ApplicantDTO applicantDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			
			applicantService.addEntity(ApplicantRequestResponseFactory.setApplicant(applicantDTO, request));
			return new UserStatus(1, "Applicant added Successfully !");
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
	
	@Transactional @RequestMapping(value = "/createMultiple", headers = "Content-Type=*/*",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody UserStatus addEmployeeAttendance(@RequestParam("file") MultipartFile file) {
		try {
			List<Applicant> applicants = new ArrayList<Applicant>();
			
	        @SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(file.getInputStream());
	        Sheet worksheet = workbook.getSheetAt(0);
	        Iterator<Row> iterator = worksheet.iterator();
	        while (iterator.hasNext()) {
	        	Applicant applicant = new Applicant();
	        	Row row = iterator.next();
	        	if(row.getRowNum()!=0){
	    /*    	Client client =new Client();
	        	client.setId((int)(row.getCell(1).getNumericCellValue()));
	        	applicant.setClient(client);
	        	
	        	Product product =new Product();
	        	product.setId((int)(row.getCell(1).getNumericCellValue()));
	        	applicant.setProduct(product);*/
	        	
	        	applicant.setApplicantId(String.valueOf(row.getCell(0).getNumericCellValue()));
	        	applicant.setApplicantName(row.getCell(1).getStringCellValue());
	        	applicant.setEmail(row.getCell(2).getStringCellValue());
	        	applicant.setMobileNumber(String.valueOf(row.getCell(3).getNumericCellValue()));
	        	applicant.setPrice((float) row.getCell(4).getNumericCellValue());
	        	applicant.setCreateDate((row.getCell(5).getDateCellValue()));
	        	applicant.setFormSubmissin(row.getCell(6).getStringCellValue());
	        	applicant.setPaidUnPaid(row.getCell(7).getStringCellValue());
	        	applicants.add(applicant);
	        	}
	        }
	        for (Applicant applicant : applicants) {
	        	long id =1;
	        	Client client = new Client();
	        	client.setId(id);
	        	Product product =  new Product();
	        	product.setId(id);
				applicant.setClient(client);
				applicant.setProduct(product);
				applicantService.addEntity(applicant);
			}
		} catch (Exception e) {
			System.out.println("Inside Exception");
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
		return null;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getApplicant(@PathVariable("id") long id) {
		Applicant applicant = null;
		try {
			applicant = applicantService.getEntityById(Applicant.class, id);
			if(applicant==null){
				return new Response(1,"There is no any Applicant");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,applicant);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateApplicant(@RequestBody ApplicantDTO applicantDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			applicantService.updateEntity(ApplicantRequestResponseFactory.setApplicantUpdate(applicantDTO, request));
			return new UserStatus(1, "Applicant update Successfully !");
		} catch (Exception e) {
			logger.error(e);
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getUserType() {

		List<Applicant> applicants = null;
		try {
			applicants = applicantService.getEntityList(Applicant.class);
			if(applicants==null){
				 logger.info("there is no any Applicant list");
				return new Response(1,"There is no any Applicant list");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,applicants);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteApplicant(@PathVariable("id") long id) {

		try {
			Applicant applicant =applicantService.getEntityById(Applicant.class, id);
			if (applicant==null) {
				 logger.info("there is no any Applicant");
				return new Response(1,"there is no any Applicant");
			}
			applicant.setIsactive(false);
			applicantService.updateEntity(applicant);
			return new Response(1, "Applicant deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}

	}
}

