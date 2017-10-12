package com.nextech.erp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
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
import com.nextech.erp.dto.ProductNewAssoicatedList;
import com.nextech.erp.factory.ProductInventoryRequestResponseFactory;
import com.nextech.erp.factory.ProductRequestResponseFactory;
import com.nextech.erp.factory.TaxStructureRequestResponseFactory;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productrawmaterialassociation;
import com.nextech.erp.model.Taxstructure;
import com.nextech.erp.newDTO.ProductDTO;
import com.nextech.erp.newDTO.TaxStructureDTO;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.NotificationUserAssociationService;
import com.nextech.erp.service.ProductRMAssoService;
import com.nextech.erp.service.ProductService;
import com.nextech.erp.service.ProductinventoryService;
import com.nextech.erp.service.TaxstructureService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.service.VendorService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;
import com.nextech.erp.util.ImageUploadUtil;

@Controller
@Transactional @RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ProductinventoryService productinventoryService;

	@Autowired
	ProductRMAssoService productRMAssoService;
	
	
	@Autowired
	NotificationService notificationService;

	@Autowired
	VendorService vendorService;

	@Autowired
	MailService mailService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ServletContext context;
	
	@Autowired
	TaxstructureService taxstructureService;
	
	@Autowired 
	NotificationUserAssociationService notificationUserAssociationService;
	
	static Logger logger = Logger.getLogger(PageController.class);
	
	
	@Transactional @RequestMapping(value = "/create",headers = "Content-Type=*/*", method = RequestMethod.POST)
	public @ResponseBody UserStatus addProduct(HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile inputFile,
			@RequestParam("clientPartNumber") String clientPartNumber,
			@RequestParam("name") String name,@RequestParam("description")String description,
			@RequestParam("partNumber") String partNumber,@RequestParam("cgst") double cgst,@RequestParam("igst") double igst,
			@RequestParam("other1") double other1,@RequestParam("other2") double other2,@RequestParam("sgst") double sgst) {
		try {
			if (productService.getProductByName(name) == null) {

			} else {
				return new UserStatus(0, messageSource.getMessage(ERPConstants.PRODUCT_NAME, null, null));
			}
			if (productService.getProductByPartNumber(partNumber) == null) {
			} else {
				return new UserStatus(0, messageSource.getMessage(ERPConstants.PART_NUMBER, null, null));
			}
			if(inputFile==null){
			    ProductDTO productDTO =	setProductDTO(clientPartNumber, name, description, partNumber);
			  //TODO Save call Tax Structure
			    TaxStructureDTO taxStructureDTO = setTaxStructureDTO(cgst, igst, other1, other2, sgst);
			    
			    long taxid =   taxstructureService.addEntity(TaxStructureRequestResponseFactory.setTaxStructure(taxStructureDTO, request));
			 
			    taxStructureDTO.setId(taxid);
			    productDTO.setTaxStructureDTO(taxStructureDTO);
			    long id =	productService.addEntity(ProductRequestResponseFactory.setProduct(productDTO, request));
		       	productDTO.setId(id); 
				addProductInventory(productDTO,Long.parseLong(request.getAttribute("current_user").toString()));
				
			}else{
				String destinationFilePath = ImageUploadUtil.imgaeUpload(inputFile);
				ProductDTO productDTO =	setProductDTO(clientPartNumber, name, description, partNumber);
				productDTO.setDesign(destinationFilePath);
				//TODO Save call Tax Structure
			    TaxStructureDTO taxStructureDTO = setTaxStructureDTO(cgst, igst, other1, other2, sgst);
			    
			      long taxid =   taxstructureService.addEntity(TaxStructureRequestResponseFactory.setTaxStructure(taxStructureDTO, request));
				 taxStructureDTO.setId(taxid);
			     productDTO.setTaxStructureDTO(taxStructureDTO);
			    long id =	productService.addEntity(ProductRequestResponseFactory.setProduct(productDTO, request));
			   	productDTO.setId(id); 
				addProductInventory(productDTO,Long.parseLong(request.getAttribute("current_user").toString()));
			}
			return new UserStatus(1, "product added Successfully !");
		} catch (ConstraintViolationException cve) {
			logger.error("Inside ConstraintViolationException");
			cve.printStackTrace();
			return new UserStatus(0, cve.getCause().getMessage());
		} catch (PersistenceException pe) {
			logger.error("Inside PersistenceException");
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (Exception e) {
			logger.error("Inside Exception");
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}

	@Transactional @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody UserStatus getProduct(@PathVariable("id") long id) {
		ProductDTO product = null;
		try {
			product = productService.getProductDTO(id);
			if(product==null){
				return new UserStatus(1,"There is no any product");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new UserStatus(1,product);
	}
	
	@Transactional @RequestMapping(value = "/update",headers = "Content-Type=*/*", method = RequestMethod.POST)
	public @ResponseBody UserStatus updateProduct(HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile inputFile,
			@RequestParam("clientPartNumber") String clientPartNumber,	@RequestParam("id") long id,
			@RequestParam("name") String name,@RequestParam("description")String description,
			@RequestParam("partNumber") String partNumber,@RequestParam("cgst") double cgst,@RequestParam("igst") double igst,
			@RequestParam("other1") double other1,@RequestParam("other2") double other2,@RequestParam("sgst") double sgst) {
		try {
			ProductDTO oldProductInfo = productService.getProductDTO(id);
			if(name.equals(oldProductInfo.getName())){ 	
				} else { 
					if (productService.getProductByName(name) == null) {
				    }else{  
				    	return new UserStatus(0, messageSource.getMessage(ERPConstants.PRODUCT_NAME, null, null));
					}
				 }
	            if(partNumber.equals(oldProductInfo.getPartNumber())){  			
				} else { if (productService.getProductByPartNumber(partNumber) == null) {
				    }else{  
				    	return new UserStatus(0, messageSource.getMessage(ERPConstants.PART_NUMBER, null, null));
					}
				 }
	    		if(inputFile==null){
				    ProductDTO productDTO =	setProductDTO(clientPartNumber, name, description, partNumber);
				    TaxStructureDTO taxStructureDTO = setTaxStructureDTO(cgst, igst, other1, other2, sgst);
					 Taxstructure taxid =   taxstructureService.updateEntity(TaxStructureRequestResponseFactory.setTaxStructure(taxStructureDTO, request));
					 taxStructureDTO.setId(taxid.getId());
					 productDTO.setTaxStructureDTO(taxStructureDTO);
				    productDTO.setId(id);
				    productService.updateEntity(ProductRequestResponseFactory.setProductUpdate(productDTO, request));
				}else{
					String destinationFilePath = ImageUploadUtil.imgaeUpload(inputFile);
					ProductDTO productDTO =	setProductDTO(clientPartNumber, name, description, partNumber);
				     TaxStructureDTO taxStructureDTO = setTaxStructureDTO(cgst, igst, other1, other2, sgst);
					Taxstructure taxid =   taxstructureService.updateEntity(TaxStructureRequestResponseFactory.setTaxStructure(taxStructureDTO, request));
					taxStructureDTO.setId(taxid.getId());
					 productDTO.setTaxStructureDTO(taxStructureDTO);
					 productDTO.setId(id);
					productDTO.setId(id);
					productDTO.setDesign(destinationFilePath);
					productService.updateEntity(ProductRequestResponseFactory.setProductUpdate(productDTO, request));
				}
			return new UserStatus(1, "product added Successfully !");
		} catch (ConstraintViolationException cve) {
			logger.error("Inside ConstraintViolationException");
			cve.printStackTrace();
			return new UserStatus(0, cve.getCause().getMessage());
		} catch (PersistenceException pe) {
			logger.error("Inside PersistenceException");
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (Exception e) {
			logger.error("Inside Exception");
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}
	
	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductList() {

		List<ProductDTO> productList = null;
		try {
			productList = productService.getProductList();
			if(productList==null){
				logger.error("There is no product list");
				return new Response(1,"There is no product list");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,productList);
	}
	@Transactional @RequestMapping(value = "/list/newProductRMAssociation", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getNewProductRMAsso() {

		List<Product> productList = null;
		List<ProductNewAssoicatedList> productNewAssoicatedLists = new ArrayList<ProductNewAssoicatedList>();
		try {
			productList = productService.getEntityList(Product.class);
			for(Product product : productList){
				List<Productrawmaterialassociation> productrawmaterialassociations = productRMAssoService.getProductRMAssoListByProductID(product.getId());
				if(productrawmaterialassociations.isEmpty()){
					ProductNewAssoicatedList productNewAssoicatedList = new ProductNewAssoicatedList();
					productNewAssoicatedList.setId(product.getId());
					productNewAssoicatedList.setPartNumber(product.getPartNumber());
					productNewAssoicatedLists.add(productNewAssoicatedList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,"New Productrawmaterialassociation List",productNewAssoicatedLists);
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteProduct(@PathVariable("id") long id) {

		try {
			ProductDTO productDTO =productService.deleteProduct(id);
			if(productDTO==null){
				logger.error("There is no product for delete");
				return new Response(1,"There is no product id");
			}
			return new Response(1, "Product deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}

	}
	
	@Transactional @RequestMapping(value = "/image/{PRODUCT-ID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<byte[]> getProductByproductId(@PathVariable("PRODUCT-ID") long productId,HttpServletRequest request) {
	
		try {
			Product product = productService.getProductByProductId(productId);
			String FILE_PATH = product.getDesign();
			
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

	private void addProductInventory(ProductDTO productDTO,long userId) throws Exception{
		productinventoryService.addEntity(ProductInventoryRequestResponseFactory.setProductIn(productDTO, userId));
	}
	
	public ProductDTO setProductDTO(String clientPartNumber,String name,String description,String partNumber){
		  ProductDTO productDTO =  new ProductDTO();
		   productDTO.setClientPartNumber(clientPartNumber);
		   productDTO.setName(name);
		   productDTO.setPartNumber(partNumber);
		   productDTO.setDescription(description);
		return productDTO;
	}
	
	public TaxStructureDTO setTaxStructureDTO(double cgst,double igst,double other1,double other2,double sgst){
		TaxStructureDTO taxStructureDTO =  new TaxStructureDTO();
		taxStructureDTO.setCgst(cgst);
		taxStructureDTO.setIgst(igst);
		taxStructureDTO.setOther1(other1);
		taxStructureDTO.setOther2(other2);
		taxStructureDTO.setSgst(sgst);
		return taxStructureDTO;
	}
}
