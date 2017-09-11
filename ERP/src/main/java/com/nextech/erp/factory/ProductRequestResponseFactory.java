package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.Product;
import com.nextech.erp.model.Taxstructure;
import com.nextech.erp.newDTO.ProductDTO;
import com.nextech.erp.newDTO.TaxStructureDTO;

public class ProductRequestResponseFactory {
	
	public static Product setProduct(ProductDTO productDTO,HttpServletRequest request){
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setClientpartnumber(productDTO.getClientPartNumber());
		product.setDescription(productDTO.getDescription());
		product.setDesign(productDTO.getDesign());
		product.setPartNumber(productDTO.getPartNumber());
		product.setName(productDTO.getName());
		product.setIsactive(true);
		Taxstructure taxstructure =  new Taxstructure();
		taxstructure.setId(productDTO.getTaxStructureDTO().getId());
		product.setTaxstructure(taxstructure);
		product.setRatePerUnit(productDTO.getRatePerUnit());
		product.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return product;
	}

	public static Product setProductUpdate(ProductDTO productDTO,HttpServletRequest request){
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setClientpartnumber(productDTO.getClientPartNumber());
		product.setDescription(productDTO.getDescription());
		product.setDesign(productDTO.getDesign());
		product.setPartNumber(productDTO.getPartNumber());
		product.setName(productDTO.getName());
		product.setDesign(productDTO.getDesign());
		product.setIsactive(true);
		product.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return product;
	}

	public static ProductDTO setProductDto(Product product){
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setRatePerUnit(product.getRatePerUnit());
		productDTO.setClientPartNumber(product.getClientpartnumber());
		productDTO.setDescription(product.getDescription());
		productDTO.setDesign(product.getDesign());
		productDTO.setPartNumber(product.getPartNumber());
		productDTO.setName(product.getName());
		TaxStructureDTO taxStructureDTO =  new TaxStructureDTO();
		taxStructureDTO.setId(product.getTaxstructure().getId());
		taxStructureDTO.setCgst(product.getTaxstructure().getCgst());
		taxStructureDTO.setIgst(product.getTaxstructure().getIgst());
		taxStructureDTO.setOther1(product.getTaxstructure().getOther1());
		taxStructureDTO.setOther2(product.getTaxstructure().getOther2());
		taxStructureDTO.setSgst(product.getTaxstructure().getSgst());
		productDTO.setTaxStructureDTO(taxStructureDTO);
		productDTO.setActive(true);
		productDTO.setCreatedBy(product.getCreatedBy());
		productDTO.setCreatedDate(product.getCreatedDate());
		productDTO.setUpdatedBy(product.getUpdatedBy());
		productDTO.setUpdatedDate(product.getUpdatedDate());
		productDTO.setDesign(product.getDesign());
		return productDTO;
	}

}
