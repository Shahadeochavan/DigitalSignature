package com.nextech.erp.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class SecurityCheckOutDTO  extends AbstractDTO{

	private String driverFirstName;
	private String driverLastName;
	private Date createDate;
	private Time inTime;
	private String invoiceNo;
	private String licenceNo;
	private Time outTime;
	private int poNo;
	private String vehicleNo;
	private String clientName;
	private List<SecurityCheckOutPart> securityCheckOutParts;
	public String getDriverFirstName() {
		return driverFirstName;
	}
	public void setDriverFirstName(String driverFirstName) {
		this.driverFirstName = driverFirstName;
	}
	public String getDriverLastName() {
		return driverLastName;
	}
	public void setDriverLastName(String driverLastName) {
		this.driverLastName = driverLastName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Time getInTime() {
		return inTime;
	}
	public void setInTime(Time inTime) {
		this.inTime = inTime;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getLicenceNo() {
		return licenceNo;
	}
	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}
	public Time getOutTime() {
		return outTime;
	}
	public void setOutTime(Time outTime) {
		this.outTime = outTime;
	}
	public int getPoNo() {
		return poNo;
	}
	public void setPoNo(int poNo) {
		this.poNo = poNo;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public List<SecurityCheckOutPart> getSecurityCheckOutParts() {
		return securityCheckOutParts;
	}
	public void setSecurityCheckOutParts(
			List<SecurityCheckOutPart> securityCheckOutParts) {
		this.securityCheckOutParts = securityCheckOutParts;
	}

	
	


}
