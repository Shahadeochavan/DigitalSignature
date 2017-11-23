package com.nextech.dscrm.dto;



public class SupportDTO extends AbstractDTO{
	
	private String companyName;

	private String emailSales;

	private String emailSupport;

	private String telephone;

	private String website;
	
	private String address;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmailSales() {
		return emailSales;
	}

	public void setEmailSales(String emailSales) {
		this.emailSales = emailSales;
	}

	public String getEmailSupport() {
		return emailSupport;
	}

	public void setEmailSupport(String emailSupport) {
		this.emailSupport = emailSupport;
	}


	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
