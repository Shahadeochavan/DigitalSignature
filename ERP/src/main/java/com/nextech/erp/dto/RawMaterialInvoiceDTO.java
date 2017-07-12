package com.nextech.erp.dto;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class RawMaterialInvoiceDTO extends AbstractDTO{
	private String invoiceNo;
	private long vendor;
	private String vehicleNo;
	private String partNumber;
	private long goodQuantity;
	private long receivedQuantity;
	private String remark;
	private boolean IsReturnInvoice;
	private String driverFirstName;
	private String driverLastName;
	private Time inTime;
	private Time outTime;
	private Date createDate;
	private int poNo;
	private String vendorName;
	private List<QualityCheckRMDTO> qualityCheckRMDTOs;
	
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public long getVendor() {
		return vendor;
	}
	public void setVendor(long vendor) {
		this.vendor = vendor;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public long getGoodQuantity() {
		return goodQuantity;
	}
	public void setGoodQuantity(long goodQuantity) {
		this.goodQuantity = goodQuantity;
	}
	public long getReceivedQuantity() {
		return receivedQuantity;
	}
	public void setReceivedQuantity(long receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public boolean isIsReturnInvoice() {
		return IsReturnInvoice;
	}
	public void setIsReturnInvoice(boolean isReturnInvoice) {
		IsReturnInvoice = isReturnInvoice;
	}
	
	public Time getInTime() {
		return inTime;
	}
	public void setInTime(Time inTime) {
		this.inTime = inTime;
	}
	public Time getOutTime() {
		return outTime;
	}
	public void setOutTime(Time outTime) {
		this.outTime = outTime;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getPoNo() {
		return poNo;
	}
	public void setPoNo(int poNo) {
		this.poNo = poNo;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public List<QualityCheckRMDTO> getQualityCheckRMDTOs() {
		return qualityCheckRMDTOs;
	}
	public void setQualityCheckRMDTOs(List<QualityCheckRMDTO> qualityCheckRMDTOs) {
		this.qualityCheckRMDTOs = qualityCheckRMDTOs;
	}
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
}