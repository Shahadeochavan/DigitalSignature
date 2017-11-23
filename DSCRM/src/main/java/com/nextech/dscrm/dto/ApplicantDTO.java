package com.nextech.dscrm.dto;

import java.util.Date;

import com.nextech.dscrm.model.Client;
import com.nextech.dscrm.model.Product;

public class ApplicantDTO extends AbstractDTO {
	
	private String fullName;
	private String  mobileNumber;
	private String emailId;
	private String customerId;
	private  Date createDate;
	private Client client;
	private Product product;
	private String validity;
	private String paidUnpaid;
	private String formSubmission;
	private float price;
	
	public ApplicantDTO(){
		
	}
	public ApplicantDTO(int id){
		this.setId(id);
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	public String getPaidUnpaid() {
		return paidUnpaid;
	}
	public void setPaidUnpaid(String paidUnpaid) {
		this.paidUnpaid = paidUnpaid;
	}
	public String getFormSubmission() {
		return formSubmission;
	}
	public void setFormSubmission(String formSubmission) {
		this.formSubmission = formSubmission;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	

}
