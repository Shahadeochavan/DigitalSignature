package com.nextech.erp.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nextech.erp.dto.BOMModelData;
import com.nextech.erp.dto.BomDTO;
import com.nextech.erp.dto.BomModelPart;
import com.nextech.erp.dto.BomRMVendorModel;
import com.nextech.erp.dto.CreatePdfForBomProduct;
import com.nextech.erp.dto.ProductBomDTO;
import com.nextech.erp.factory.BOMFactory;
import com.nextech.erp.factory.BomRMVendorRequestResponseFactory;
import com.nextech.erp.newDTO.BomRMVendorAssociationsDTO;
import com.nextech.erp.newDTO.ProductDTO;
import com.nextech.erp.newDTO.RMVendorAssociationDTO;
import com.nextech.erp.newDTO.RawMaterialDTO;
import com.nextech.erp.newDTO.VendorDTO;
import com.nextech.erp.service.BOMRMVendorAssociationService;
import com.nextech.erp.service.BomService;
import com.nextech.erp.service.ProductService;
import com.nextech.erp.service.RMVAssoService;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.service.VendorService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;

@RestController
@RequestMapping("/bom")
public class BomController {

	@Autowired
	BomService bomService;
	
	@Autowired 
	ProductService productService;
	
	@Autowired
	RawmaterialService rawmaterialService;
	
	@Autowired
	BOMRMVendorAssociationService bOMRMVendorAssociationService;
	
	@Autowired
	VendorService vendorService;
	
	@Autowired
	RMVAssoService rMVAssoService;
	
	@RequestMapping(value = "/createmultiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addMultipleBom(
			@Valid @RequestBody BomDTO bomDTO, BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			
			    // TODO save call bom
			BomDTO bomDTO2 = bomService.addMultipleBom(bomDTO, request, response);
			bomDTO.setId(bomDTO2.getId());
			bomDTO.setProduct(bomDTO2.getProduct());
			addBomRMVendorAsso(bomDTO, request, response);
			

			return new UserStatus(1, "Bom added Successfully !");
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
	public @ResponseBody Response getUnit(@PathVariable("id") long id) {
		BomDTO bom = null;
		try {
			bom = bomService.getBomById(id);
			if(bom==null){
				return new Response(1,"There is no any bom");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,bom);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateUnit(@RequestBody BomDTO bomDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			bomDTO.setUpdatedBy((long) request.getAttribute("current_user"));
			bomService.updateEntity(BOMFactory.setBom(bomDTO,request));
			return new UserStatus(1, "Bom update Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getBom(HttpServletRequest request,HttpServletResponse response) throws IOException {

		List<BomDTO> bomList = null;
		try {
			bomList = bomService.getBomList();
			if(bomList==null){
				return  new Response(1,"There is no any bom list");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		//  downloadPDF(request, response, bomList);
		return new Response(1,bomList);
	}
	
	@RequestMapping(value = "/BomCompletedList", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getBomCompleted(HttpServletRequest request,HttpServletResponse response) throws IOException {

		List<BomDTO> bomList = null;
		List<BOMModelData> bomModelDatas = new ArrayList<BOMModelData>(); 
		try {
			bomList = bomService.getBomList();
			for (BomDTO bom : bomList) {
				ProductDTO product = productService.getProductDTO(bom.getProduct().getId());
				BOMModelData bomModelData = new BOMModelData();
				bomModelData.setPartNumber(product.getPartNumber());
				bomModelData.setId(product.getId());
				bomModelDatas.add(bomModelData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		//  downloadPDF(request, response, bomList);
		return new Response(1, bomModelDatas);
	}
	
	@RequestMapping(value = "/getProductList", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductList() throws IOException {

		List<ProductDTO> products = null;
		List<Long> productIdList = null; 
		Response response = null;
		try {
			productIdList = bomService.getProductList();
			products = productService.getProductList(productIdList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if(products == null){
			response = new Response(0,"Please add Product RM Association to Create BOM", products);
		}else{
			response = new Response(1,"Success", products);
		}
		return response;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteClient(@PathVariable("id") long id) {

		try {
		BomDTO bomDTO =	bomService.deleteBom(id);
		if(bomDTO==null){
			return  new Response(1,"There is no any bom");
		}
			return new Response(1, "Bom deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}

	}
	
	@RequestMapping(value = "bomList/{PRODUCT-ID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getBomByProductId(@PathVariable("PRODUCT-ID") long productId) {

		List<BomDTO> boList = null;
		try {
			boList = bomService.getBomListByProductId(productId);
			if(boList==null){
				return new Response(1,"There is no any bom list");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response(1,boList);
	}
	@RequestMapping(value = "downloadBomPdf/{PRODUCT-ID}/{BOM-ID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getBomPdfByProductIdAndBomId(@PathVariable("PRODUCT-ID") long productId,@PathVariable("BOM-ID") long bomId,HttpServletRequest request, HttpServletResponse response) throws IOException {

		List<BomDTO> boList = null;
		List<BomRMVendorModel> bomRMVendorModels = new ArrayList<BomRMVendorModel>();
		ProductBomDTO productBomDTO = new ProductBomDTO();
		try {
			boList = bomService.getBomListByProductIdAndBomId(productId, bomId);
			if(boList!=null){
			for (BomDTO bom : boList) {
				List<BomRMVendorAssociationsDTO> bOMRMVendorAssociations = bOMRMVendorAssociationService.getBomRMVendorByBomId(bom.getId());
				if(bOMRMVendorAssociations!=null){
				for (BomRMVendorAssociationsDTO bomrmVendorAssociation : bOMRMVendorAssociations) {
					BomRMVendorModel bomRMVendorModel  = new BomRMVendorModel();
					RawMaterialDTO rawmaterial = rawmaterialService.getRMDTO(bomrmVendorAssociation.getRawmaterialId().getId());
					VendorDTO vendor = vendorService.getVendorById(bomrmVendorAssociation.getVendorId().getId());
					ProductDTO product = productService.getProductDTO( bom.getProduct().getId());
				//	RMVendorAssociationDTO rawmaterialvendorassociation = rMVAssoService.getRMVendor(rawmaterial.getId());
					bomRMVendorModel.setDescription(rawmaterial.getDescription());
					bomRMVendorModel.setVendorName(vendor.getCompanyName());
					bomRMVendorModel.setProductName(product.getName());
					bomRMVendorModel.setPricePerUnit(rawmaterial.getPricePerUnit());
					bomRMVendorModel.setQuantity(bomrmVendorAssociation.getQuantity());
					bomRMVendorModel.setAmount(bomrmVendorAssociation.getQuantity()*rawmaterial.getPricePerUnit());
					productBomDTO.setClinetPartNumber(product.getClientPartNumber());
					productBomDTO.setProductPartNumber(product.getPartNumber()); 
					productBomDTO.setCreatedDate(bom.getCreatedDate());
					bomRMVendorModels.add(bomRMVendorModel);
					
				}
				}else{
					return new Response(1,"There is no any bom rm vendor association");
				}
				
			}
			}else{
				return new Response(1,"There is no any bom list for download pdf");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    downloadPDF(request, response, bomRMVendorModels,productBomDTO);
//		return boList;
	return new Response(1,"Pdf downloaded successfully");
	}
	
	private void addBomRMVendorAsso(BomDTO bomDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		List<BomModelPart> bomModelParts = bomDTO.getBomModelParts();
		if (bomModelParts != null	&& !bomModelParts.isEmpty()) {
			for (BomModelPart bomModelPart : bomModelParts) {
				bomModelPart.setId(bomDTO.getId());
				bOMRMVendorAssociationService.addEntity(BomRMVendorRequestResponseFactory.setBomVendorAsso(bomModelPart, request));
				
			}
		}
	}

	public void downloadPDF(HttpServletRequest request, HttpServletResponse response,List<BomRMVendorModel> bomRMVendorModels,ProductBomDTO productBomDTO) throws IOException {

		final ServletContext servletContext = request.getSession().getServletContext();
	    final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
	    final String temperotyFilePath = tempDirectory.getAbsolutePath();

	    String fileName = "bom.pdf";
	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "attachment; filename="+ fileName);

	    try {

	    	CreatePdfForBomProduct createPdfForBomProduct = new CreatePdfForBomProduct();
	    	createPdfForBomProduct.createPDF(temperotyFilePath+"\\"+fileName,bomRMVendorModels,productBomDTO);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        baos = convertPDFToByteArrayOutputStream(temperotyFilePath+"\\"+fileName);
	        OutputStream os = response.getOutputStream();
	        baos.writeTo(os);
	        os.flush();
	        
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }
	}

	private ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName) throws Exception {

		InputStream inputStream = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {

			inputStream = new FileInputStream(fileName);
			byte[] buffer = new byte[1024];
			baos = new ByteArrayOutputStream();

			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return baos;
	}
}

