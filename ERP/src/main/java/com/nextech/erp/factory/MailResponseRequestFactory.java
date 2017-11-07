package com.nextech.erp.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nextech.erp.dto.DispatchDTO;
import com.nextech.erp.dto.DispatchProductDTO;
import com.nextech.erp.dto.ProductOrderDTO;
import com.nextech.erp.dto.ProductOrderData;
import com.nextech.erp.dto.QualityCheckRMDTO;
import com.nextech.erp.dto.RMOrderModelData;
import com.nextech.erp.dto.RawmaterialOrderDTO;
import com.nextech.erp.model.Rawmaterialorder;
import com.nextech.erp.model.Vendor;
import com.nextech.erp.newDTO.ClientDTO;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.newDTO.UserDTO;
import com.nextech.erp.newDTO.VendorDTO;

public class MailResponseRequestFactory {

	public static Map<String, Object> setMailDetailsUser(UserDTO userDTO) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("firstName", userDTO.getFirstName());
		model.put("lastName", userDTO.getLastName());
		model.put("email", userDTO.getEmailId());
		model.put("userId", userDTO.getUserId());
		model.put("password", userDTO.getPassword());
		model.put("location", "Pune");
		model.put("signature", "www.NextechServices.in");
		return model;
	}
	
	public static Map<String, Object> setMailDetailsVendor(VendorDTO vendorDTO) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("firstName", vendorDTO.getFirstName());
		model.put("lastName", vendorDTO.getLastName());
		model.put("email", vendorDTO.getEmail());
		 model.put("contactNumber", vendorDTO.getContactNumberMobile());
		model.put("location", "Pune");
		model.put("signature", "www.NextechServices.in");
		return model;
	}
	
	public static Map<String,Object> setMailDetailsClient(ClientDTO clientDTO){
		 Map < String, Object > model = new HashMap < String, Object > ();
	        model.put("firstName", clientDTO.getCompanyName());
	        model.put("email", clientDTO.getEmailId());
	        model.put("contactNumber", clientDTO.getContactNumber());
	        model.put("location", "Pune");
	        model.put("signature", "www.NextechServices.in");
	        return model;
	}
	
	public static Map<String,Object> setMailDetailsDispatch(ClientDTO clientDTO,List<DispatchProductDTO> dispatchProductDTOs,DispatchDTO dispatchDTO){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("firstName", clientDTO.getCompanyName());
		model.put("mailfrom", "Nextech");
		model.put("address", clientDTO.getAddress());
		model.put("dispatchProductDTOs", dispatchProductDTOs);
		model.put("invoiceNo", dispatchDTO.getInvoiceNo());
		model.put("discription", dispatchDTO.getDescription());
		model.put("location", "Pune");
		model.put("signature", "www.NextechServices.in");
	        return model;
	}
	
	public static Map<String,Object> setMailDetailsProductOrder(NotificationDTO notification,List<ProductOrderData> productOrderDatas,ClientDTO clientDTO,ProductOrderDTO productOrderDTO){
	       Map < String, Object > model = new HashMap < String, Object >();
	        model.put("companyName", clientDTO.getCompanyName());
	        model.put("mailfrom", notification.getName());
	        model.put("location", "Pune");
	        model.put("productOrderDatas",productOrderDatas);
	        model.put("invoiceNumber",productOrderDTO.getInvoiceNo());
	        model.put("date",productOrderDTO.getCreatedDate());
	        model.put("address", clientDTO.getAddress());
	        model.put("signature", "www.NextechServices.in");
	        return model;
	}
	
	public static Map<String,Object> setMailDetailsRMOrder(NotificationDTO notification,RawmaterialOrderDTO rawmaterialOrderDTO,VendorDTO vendor,List<RMOrderModelData> rmOrderModelDatas){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("firstName", vendor.getFirstName());
		model.put("lastName", vendor.getLastName());
		model.put("location", "Pune");
		model.put("rmOrderModelDatas", rmOrderModelDatas);
		model.put("address", vendor.getAddress());
		model.put("companyName", vendor.getCompanyName());
		model.put("tax", rawmaterialOrderDTO.getTax());
		model.put("mailFrom", notification.getName());
		model.put("signature", "www.NextechServices.in");
	        return model;
	}
	
	public static Map<String,Object> setMailDetailsRMInvoice(NotificationDTO notification,Rawmaterialorder rawmaterialOrderDTO,Vendor vendor,List<RMOrderModelData> rmOrderModelDatas){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("firstName", vendor.getFirstName());
		model.put("lastName", vendor.getLastName());
		model.put("location", "Pune");
		model.put("rmOrderModelDatas", rmOrderModelDatas);
		model.put("address", vendor.getAddress());
		model.put("companyName", vendor.getCompanyName());
		model.put("tax", rawmaterialOrderDTO.getTax());
		model.put("mailFrom", notification.getName());
		model.put("signature", "www.NextechServices.in");
	        return model;
	}
	
	public static Map<String,Object> setMailDetailsRMQuantity(NotificationDTO notification,Vendor vendor,List<QualityCheckRMDTO> qualityCheckRMDTOs){
        Map < String, Object > model = new HashMap < String, Object > ();
        model.put("firstName", vendor.getFirstName());
        model.put("qualityCheckRMDTOs", qualityCheckRMDTOs);
        model.put("address", vendor.getAddress());
        model.put("companyName", vendor.getCompanyName());
        model.put("lastName", vendor.getLastName());
        model.put("location", "Pune");
        model.put("signature", "www.NextechServices.in");
	        return model;
	}
}
