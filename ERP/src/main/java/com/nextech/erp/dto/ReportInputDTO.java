package com.nextech.erp.dto;

import java.util.List;

public class ReportInputDTO {
	private long id;
	private String displayName;
	private String inputType;
	private Object value;
	private List<ReportInputDataDTO> data;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public List<ReportInputDataDTO> getData() {
		return data;
	}
	public void setData(List<ReportInputDataDTO> data) {
		this.data = data;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
}