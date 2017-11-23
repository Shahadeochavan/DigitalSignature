package com.nextech.dscrm.newDTO;


import com.nextech.dscrm.dto.AbstractDTO;


public class ClientDTO extends AbstractDTO{

	private String address;
	private String commisionerate;
	private String companyName;
	private String contactNumber;
	private String contactPersonName;
	private String cst;
	private String customerEccNumber;
	private String division;
	private String emailId;
	private String  renge;
	private String vatNo;
	
	public  ClientDTO(){
		
	}
	public  ClientDTO(int id){
		this.setId(id);
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCommisionerate() {
		return commisionerate;
	}
	public void setCommisionerate(String commisionerate) {
		this.commisionerate = commisionerate;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getContactPersonName() {
		return contactPersonName;
	}
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}
	public String getCst() {
		return cst;
	}
	public void setCst(String cst) {
		this.cst = cst;
	}
	public String getCustomerEccNumber() {
		return customerEccNumber;
	}
	public void setCustomerEccNumber(String customerEccNumber) {
		this.customerEccNumber = customerEccNumber;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getRenge() {
		return renge;
	}
	public void setRenge(String renge) {
		this.renge = renge;
	}
	public String getVatNo() {
		return vatNo;
	}
	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}
}