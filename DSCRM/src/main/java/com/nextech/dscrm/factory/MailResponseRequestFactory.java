package com.nextech.dscrm.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nextech.dscrm.dto.ProductOrderDTO;
import com.nextech.dscrm.model.Vendor;
import com.nextech.dscrm.newDTO.ClientDTO;
import com.nextech.dscrm.newDTO.NotificationDTO;
import com.nextech.dscrm.newDTO.UserDTO;
import com.nextech.dscrm.newDTO.VendorDTO;

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
	
	
}
