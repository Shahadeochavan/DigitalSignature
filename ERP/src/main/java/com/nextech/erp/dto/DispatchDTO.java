package com.nextech.erp.dto;

import java.util.List;

public class DispatchDTO {
	private long orderId;
	private String invoiceNo;
	private List<DispatchPartDTO> dispatchPartDTOs;
	private String description;
	
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<DispatchPartDTO> getDispatchPartDTOs() {
		return dispatchPartDTOs;
	}
	public void setDispatchPartDTOs(List<DispatchPartDTO> dispatchPartDTOs) {
		this.dispatchPartDTOs = dispatchPartDTOs;
	}
	
}
