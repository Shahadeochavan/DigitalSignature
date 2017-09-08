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
import com.nextech.erp.newDTO.RMTypeDTO;
import com.nextech.erp.newDTO.RMVendorAssociationDTO;
import com.nextech.erp.newDTO.RawMaterialDTO;
import com.nextech.erp.newDTO.UnitDTO;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.service.RawmaterialinventoryService;
import com.nextech.erp.status.UserStatus;
import com.nextech.erp.util.ImageUploadUtil;

@Controller
@Transactional
@RequestMapping("/rawmaterial")
public class RawmaterialController {

	@Autowired
	RawmaterialService rawmaterialService;

	@Autowired
	RawmaterialinventoryService rawmaterialinventoryService;

	@Autowired
	private MessageSource messageSource;

	@Transactional
	@RequestMapping(value = "/create", headers = "Content-Type=*/*", method = RequestMethod.POST)
	public @ResponseBody UserStatus addRawmaterial(HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile inputFile,
			@RequestParam("rmName") String rmName, @RequestParam("partNumber") String partNumber,
			@RequestParam("description") String description, @RequestParam("pricePerUnit") float pricePerUnit,
			@RequestParam("unitId") long unitId, @RequestParam("rmTypeId") long rmTypeId) {
		try {
			if(inputFile==null){
			RawMaterialDTO  rawMaterialDTO =	setRM(rmName, description, pricePerUnit, partNumber, unitId, rmTypeId);
			long id = rawmaterialService.addEntity(RMRequestResponseFactory.setRawMaterial(rawMaterialDTO, request));
			rawMaterialDTO.setId(id);
			addRMInventory(rawMaterialDTO, Long.parseLong(request.getAttribute("current_user").toString()));
			}else{
				String destinationFilePath = ImageUploadUtil.imgaeUpload(inputFile);
				RawMaterialDTO  rawMaterialDTO =	setRM(rmName, description, pricePerUnit, partNumber, unitId, rmTypeId);
				rawMaterialDTO.setDesign(destinationFilePath);
				long id = rawmaterialService.addEntity(RMRequestResponseFactory.setRawMaterial(rawMaterialDTO, request));
				rawMaterialDTO.setId(id);
				addRMInventory(rawMaterialDTO, Long.parseLong(request.getAttribute("current_user").toString()));
			}
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

	@Transactional
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody UserStatus getRawmaterial(@PathVariable("id") long id) {
		RawMaterialDTO rawmaterial = null;
		try {
			rawmaterial = rawmaterialService.getRMDTO(id);
			if (rawmaterial == null) {
				return new UserStatus(1, "There is no any rm");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new UserStatus(1, rawmaterial);
	}

	@Transactional
	@RequestMapping(value = "/update", headers = "Content-Type=*/*", method = RequestMethod.POST)
	public @ResponseBody UserStatus upddateRawmaterial(HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile inputFile,
			@RequestParam("rmName") String rmName,
			@RequestParam("id") long id, @RequestParam("partNumber") String partNumber,
			@RequestParam("description") String description, @RequestParam("pricePerUnit") float pricePerUnit,
			@RequestParam("unitId") long unitId, @RequestParam("rmTypeId") long rmTypeId) {
		try {
			if(inputFile==null){
				RawMaterialDTO  rawMaterialDTO =	setRM(rmName, description, pricePerUnit, partNumber, unitId, rmTypeId);
				rawMaterialDTO.setId(id);
				rawmaterialService.updateEntity(RMRequestResponseFactory.setRawMaterialUpdate(rawMaterialDTO, request));
			}else{
				String destinationFilePath = ImageUploadUtil.imgaeUpload(inputFile);
				RawMaterialDTO  rawMaterialDTO =	setRM(rmName, description, pricePerUnit, partNumber, unitId, rmTypeId);
				rawMaterialDTO.setId(id);
				rawMaterialDTO.setDesign(destinationFilePath);
				rawmaterialService.updateEntity(RMRequestResponseFactory.setRawMaterialUpdate(rawMaterialDTO, request));
			}
			return new UserStatus(1, messageSource.getMessage(ERPConstants.RAW_MATERAIL_UPDATE, null, null));
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional
	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody UserStatus getRawmaterial() {
		List<RawMaterialDTO> rawmaterialList = null;
		try {
			rawmaterialList = rawmaterialService.getRMList();
			if (rawmaterialList.isEmpty()) {
				return new UserStatus(1, "There is no any rm list");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new UserStatus(1, rawmaterialList);
	}

	@Transactional
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteRawmaterial(@PathVariable("id") long id) {
		try {
			RawMaterialDTO rawMaterialDTO = rawmaterialService.deleteRM(id);
			if (rawMaterialDTO == null) {
				return new UserStatus(1, "There is no any rm");
			}
			return new UserStatus(1, messageSource.getMessage(ERPConstants.RAW_MATERAIL_DELETE, null, null));
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional
	@RequestMapping(value = "/getRMaterial/{VendorId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody UserStatus getRawmaterialForVendor(@PathVariable("VendorId") long id) {
		List<RMVendorAssociationDTO> rawmaterialvendorassociationList = null;
		try {
			rawmaterialvendorassociationList = rawmaterialService.getRawmaterialByVenodrId(id);
			if (rawmaterialvendorassociationList.isEmpty()) {
				return new UserStatus(1, "There is no any rm assocition list");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new UserStatus(1, rawmaterialvendorassociationList);
	}

	@Transactional
	@RequestMapping(value = "/getRMForRMOrder/{RMOrderId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody UserStatus getRawmaterialForRMOrder(@PathVariable("RMOrderId") long id) {
		List<RawMaterialDTO> rawmaterialList = null;
		try {
			rawmaterialList = rawmaterialService.getRawMaterialByRMOrderId(id);
			if (rawmaterialList.isEmpty()) {
				return new UserStatus(1, "There is no any rm list");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new UserStatus(1, rawmaterialList);
	}

	private void addRMInventory(RawMaterialDTO rawMaterialDTO, long userId) throws Exception {
		rawmaterialinventoryService.addEntity(RMRequestResponseFactory.setRMIn(rawMaterialDTO, userId));
	}

	@Transactional
	@RequestMapping(value = "/getRMaterialList/{RMTypeId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody UserStatus getRawmaterialForRMType(@PathVariable("RMTypeId") long id) {
		List<RawMaterialDTO> rawmaterialList = null;
		try {
			rawmaterialList = rawmaterialService.getRMByRMTypeId(id);
			if (rawmaterialList.isEmpty()) {
				return new UserStatus(1, "There is no any rm list");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new UserStatus(1, rawmaterialList);
	}

	@Transactional
	@RequestMapping(value = "/image/{RM-ID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<byte[]> getRawmaterailImage(@PathVariable("RM-ID") long id,
			HttpServletRequest request) {
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
	public RawMaterialDTO setRM(String rmName,String description,float pricePerUnit,String partNumber,long unitId,long rmTypeId){
		RawMaterialDTO rawMaterialDTO = new RawMaterialDTO();
		rawMaterialDTO.setRmName(rmName);
		rawMaterialDTO.setDescription(description);
		rawMaterialDTO.setPricePerUnit(pricePerUnit);
		rawMaterialDTO.setPartNumber(partNumber);
		UnitDTO unit = new UnitDTO();
		unit.setId(unitId);
		rawMaterialDTO.setUnitId(unit);
		RMTypeDTO rmtype = new RMTypeDTO();
		rmtype.setId(rmTypeId);
		rawMaterialDTO.setRmTypeId(rmtype);
		return rawMaterialDTO;
	}
}