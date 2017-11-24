package com.nextech.dscrm.newDTO;


import java.util.List;

import com.nextech.dscrm.dto.AbstractDTO;

public class VendorDTO extends AbstractDTO{

	private String address;
	private String city;
	private String commisionerate;
	private String companyName;
	private String contactNumberMobile;
	private String contactNumberOffice;
	private String cst;
	private String customerEccNumber;
	private String divison;
	private String email;
	private String firstName;
	private boolean isactive;
	private String lastName;
	private String postalcode;
	private String renge; 
	private String state;
	private String vatNo;
	
	public VendorDTO(){
		
	}
	public VendorDTO(int id){
		this.setId(id);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getContactNumberMobile() {
		return contactNumberMobile;
	}

	public void setContactNumberMobile(String contactNumberMobile) {
		this.contactNumberMobile = contactNumberMobile;
	}

	public String getContactNumberOffice() {
		return contactNumberOffice;
	}

	public void setContactNumberOffice(String contactNumberOffice) {
		this.contactNumberOffice = contactNumberOffice;
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

	public String getDivison() {
		return divison;
	}

	public void setDivison(String divison) {
		this.divison = divison;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getRenge() {
		return renge;
	}

	public void setRenge(String renge) {
		this.renge = renge;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getVatNo() {
		return vatNo;
	}

	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}
	
}
