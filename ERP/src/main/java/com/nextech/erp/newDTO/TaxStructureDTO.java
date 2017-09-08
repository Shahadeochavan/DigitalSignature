package com.nextech.erp.newDTO;

import com.nextech.erp.dto.AbstractDTO;

public class TaxStructureDTO extends AbstractDTO{
	private double cgst;
	private double igst;
	private double other1;
	private double other2;
	private double sgst;
	
	public TaxStructureDTO(){
		
	}
     public TaxStructureDTO(int id){
		this.setId(id);
	}
	public double getCgst() {
		return cgst;
	}
	public void setCgst(double cgst) {
		this.cgst = cgst;
	}
	public double getIgst() {
		return igst;
	}
	public void setIgst(double igst) {
		this.igst = igst;
	}
	public double getOther1() {
		return other1;
	}
	public void setOther1(double other1) {
		this.other1 = other1;
	}
	public double getOther2() {
		return other2;
	}
	public void setOther2(double other2) {
		this.other2 = other2;
	}
	public double getSgst() {
		return sgst;
	}
	public void setSgst(double sgst) {
		this.sgst = sgst;
	}
	
}
