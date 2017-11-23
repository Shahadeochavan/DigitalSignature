package com.nextech.dscrm.dto;

import com.nextech.dscrm.newDTO.ClientDTO;
import com.nextech.dscrm.newDTO.ProductDTO;

public class ClientproductassoDTO extends AbstractDTO{
	
	private ProductDTO product;
	private ClientDTO client;
	private float pricePerUnit;

   public  ClientproductassoDTO(){
		
	}
   
   public  ClientproductassoDTO(int id){
		this.setId(id);
	}
	
	public ProductDTO getProduct() {
		return product;
	}
	public void setProduct(ProductDTO product) {
		this.product = product;
	}
	public ClientDTO getClient() {
		return client;
	}
	public void setClient(ClientDTO client) {
		this.client = client;
	}
	public float getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(float pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
}
