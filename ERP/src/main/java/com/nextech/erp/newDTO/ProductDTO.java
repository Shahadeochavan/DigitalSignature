package com.nextech.erp.newDTO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nextech.erp.dto.AbstractDTO;



public class ProductDTO  extends AbstractDTO{

	private String clientPartNumber;
	private String design;
	private String name;
	private String partNumber;
	private long ratePerUnit;
	private List<MultipartFile> files;
	public String getClientPartNumber() {
		return clientPartNumber;
	}
	public void setClientPartNumber(String clientPartNumber) {
		this.clientPartNumber = clientPartNumber;
	}
	public String getDesign() {
		return design;
	}
	public void setDesign(String design) {
		this.design = design;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public long getRatePerUnit() {
		return ratePerUnit;
	}
	public void setRatePerUnit(long ratePerUnit) {
		this.ratePerUnit = ratePerUnit;
	}
	public List<MultipartFile> getFiles() {
		return files;
	}
	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	

}
