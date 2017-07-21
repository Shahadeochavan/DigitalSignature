package com.nextech.erp.dto;

import java.util.List;

public class DispatchDTO extends AbstractDTO {
	private long orderId;
	private String invoiceNo;
	private List<DispatchPartDTO> dispatchPartDTOs;
	
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
	public List<DispatchPartDTO> getDispatchPartDTOs() {
		return dispatchPartDTOs;
	}
	public void setDispatchPartDTOs(List<DispatchPartDTO> dispatchPartDTOs) {
		this.dispatchPartDTOs = dispatchPartDTOs;
	}
	
}
