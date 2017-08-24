package com.nextech.erp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.factory.RMRequestResponseFactory;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rmtype;
import com.nextech.erp.model.Unit;
import com.nextech.erp.newDTO.RMVendorAssociationDTO;
import com.nextech.erp.newDTO.RawMaterialDTO;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.service.RawmaterialinventoryService;
import com.nextech.erp.status.UserStatus;
import com.nextech.erp.util.ImageUploadUtil;
@Controller
@Transactional @RequestMapping("/rawmaterial")
public class RawmaterialController {

	@Autowired
	RawmaterialService rawmaterialService;
	
	@Autowired
	RawmaterialinventoryService rawmaterialinventoryService;

	@Autowired
	private MessageSource messageSource;

	@Transactional @RequestMapping(value = "/create",headers = "Content-Type=*/*", method = RequestMethod.POST)
	public @ResponseBody UserStatus addRawmaterial(
			HttpServletRequest request,@RequestParam("file") MultipartFile inputFile,
			@RequestParam("rmName") String rmName,
			@RequestParam("partNumber") String partNumber,@RequestParam("description")String description,
			@RequestParam("pricePerUnit") float pricePerUnit,
			@RequestParam("unitId") long unitId,@RequestParam("rmTypeId") long rmTypeId ) {
		try {
			String destinationFilePath = ImageUploadUtil.imgaeUpload(inputFile);
			  RawMaterialDTO rawMaterialDTO =  new RawMaterialDTO();
			  rawMaterialDTO.setRmName(rmName);
			  rawMaterialDTO.setDescription(description);
			  rawMaterialDTO.setDesign(destinationFilePath);
			  rawMaterialDTO.setPricePerUnit(pricePerUnit);
			  rawMaterialDTO.setPartNumber(partNumber);
			   Unit unit =  new Unit();
			   unit.setId(unitId);
			   rawMaterialDTO.setUnitId(unit);
			    Rmtype rmtype =  new Rmtype();
			   rmtype.setId(rmTypeId);
			   rawMaterialDTO.setRmTypeId(rmtype);
				long id = rawmaterialService.addEntity(RMRequestResponseFactory.setRawMaterial(rawMaterialDTO, request));
				rawMaterialDTO.setId(id);
				addRMInventory(rawMaterialDTO, Long.parseLong(request.getAttribute("current_user").toString()));
				return new UserStatus(1, messageSource.getMessage(ERPConstants.RAW_MATERAIL_ADD, null, null));
		} catch (ConstraintViolationException cve) {
			cve.printStackTrace();
			return new UserStatus(0, cve.getCause().getMessage());
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}
	@Transactional @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody RawMaterialDTO getRawmaterial(@PathVariable("id") long id) {
		RawMaterialDTO rawmaterial = null;
		try {
			rawmaterial = rawmaterialService.getRMDTO(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawmaterial;
	}

	@Transactional @RequestMapping(value = "/update",headers = "Content-Type=*/*", method = RequestMethod.POST)
	public @ResponseBody UserStatus upddateRawmaterial(
			HttpServletRequest request,@RequestParam("file") MultipartFile inputFile,
			@RequestParam("rmName") String rmName,@RequestParam("id") long id,
			@RequestParam("partNumber") String partNumber,@RequestParam("description")String description,
			@RequestParam("pricePerUnit") float pricePerUnit,
			@RequestParam("unitId") long unitId,@RequestParam("rmTypeId") long rmTypeId ) {
		try {
			String destinationFilePath = ImageUploadUtil.imgaeUpload(inputFile);
			  RawMaterialDTO rawMaterialDTO =  new RawMaterialDTO();
			  rawMaterialDTO.setRmName(rmName);
			  rawMaterialDTO.setDescription(description);
			  rawMaterialDTO.setDesign(destinationFilePath);
			  rawMaterialDTO.setPricePerUnit(pricePerUnit);
			  rawMaterialDTO.setPartNumber(partNumber);
			  rawMaterialDTO.setId(id);
			   Unit unit =  new Unit();
			   unit.setId(unitId);
			   rawMaterialDTO.setUnitId(unit);
			    Rmtype rmtype =  new Rmtype();
			   rmtype.setId(rmTypeId);
			   rawMaterialDTO.setRmTypeId(rmtype);
			rawmaterialService.updateEntity(RMRequestResponseFactory.setRawMaterialUpdate(rawMaterialDTO, request));
			return new UserStatus(1,messageSource.getMessage(ERPConstants.RAW_MATERAIL_UPDATE, null, null));
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<RawMaterialDTO> getRawmaterial() {
		List<RawMaterialDTO> rawmaterialList = null;
		try {
			rawmaterialList = rawmaterialService.getRMList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawmaterialList;
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteRawmaterial(@PathVariable("id") long id) {
		try {
			rawmaterialService.deleteRM(id);
			return new UserStatus(1, messageSource.getMessage(ERPConstants.RAW_MATERAIL_DELETE, null, null));
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/getRMaterial/{VendorId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<RMVendorAssociationDTO> getRawmaterialForVendor(@PathVariable("VendorId") long id) {
		List<RMVendorAssociationDTO> rawmaterialvendorassociationList = null;
		try {
			rawmaterialvendorassociationList = rawmaterialService.getRawmaterialByVenodrId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawmaterialvendorassociationList;
	}

	@Transactional @RequestMapping(value = "/getRMForRMOrder/{RMOrderId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<RawMaterialDTO> getRawmaterialForRMOrder(@PathVariable("RMOrderId") long id) {
		List<RawMaterialDTO> rawmaterialList = null;
		try {
			rawmaterialList = rawmaterialService.getRawMaterialByRMOrderId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawmaterialList;
	}

	private void addRMInventory(RawMaterialDTO rawMaterialDTO,long userId) throws Exception{
		rawmaterialinventoryService.addEntity(RMRequestResponseFactory.setRMIn(rawMaterialDTO,userId));
	}
	
	@Transactional @RequestMapping(value = "/getRMaterialList/{RMTypeId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<RawMaterialDTO> getRawmaterialForRMType(@PathVariable("RMTypeId") long id) {
		List<RawMaterialDTO> rawmaterialList = null;
		try {
			rawmaterialList = rawmaterialService.getRMByRMTypeId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawmaterialList;
	}
	@Transactional @RequestMapping(value = "/image/{RM-ID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<byte[]> getRawmaterailImage(@PathVariable("RM-ID") long id,HttpServletRequest request){
		try {
			Rawmaterial rawmaterial = rawmaterialService.getRMByRMId(id);
			String FILE_PATH = rawmaterial.getDesign();
			
			InputStream in = request.getServletContext().getResourceAsStream(FILE_PATH);
			in = new FileInputStream(new File(FILE_PATH));
		    final HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.IMAGE_PNG);

		    return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}