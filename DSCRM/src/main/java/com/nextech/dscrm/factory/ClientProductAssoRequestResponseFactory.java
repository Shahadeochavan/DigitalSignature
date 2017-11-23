package com.nextech.dscrm.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.dscrm.dto.ClientproductassoDTO;
import com.nextech.dscrm.model.Client;
import com.nextech.dscrm.model.Clientproductasso;
import com.nextech.dscrm.model.Product;

public class ClientProductAssoRequestResponseFactory {
	
	public static Clientproductasso setClientProductAsso(ClientproductassoDTO clientproductassoDTO,HttpServletRequest request){
		Clientproductasso clientproductasso =  new Clientproductasso();
		Client client = new Client();
		client.setId(clientproductassoDTO.getClient().getId());
		clientproductasso.setClient(client);
		Product product = new Product();
		product.setId(clientproductassoDTO.getProduct().getId());
		clientproductasso.setProduct(product);
		clientproductasso.setPricePerUnit(clientproductassoDTO.getPricePerUnit());
		clientproductasso.setIsactive(true);
		return clientproductasso;
		
	}
	
	public static Clientproductasso setClientProductAssoUpdate(ClientproductassoDTO clientproductassoDTO,HttpServletRequest request){
		Clientproductasso clientproductasso =  new Clientproductasso();
		Client client = new Client();
		client.setId(clientproductassoDTO.getClient().getId());
		clientproductasso.setClient(client);
		Product product = new Product();
		product.setId(clientproductassoDTO.getProduct().getId());
		clientproductasso.setProduct(product);
		clientproductasso.setPricePerUnit(clientproductassoDTO.getPricePerUnit());
		clientproductasso.setIsactive(true);
		clientproductasso.setId(clientproductassoDTO.getId());
		return clientproductasso;
		
	}

}
