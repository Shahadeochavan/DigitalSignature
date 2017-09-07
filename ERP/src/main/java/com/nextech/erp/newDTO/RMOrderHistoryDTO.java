package com.nextech.erp.newDTO;


import com.nextech.erp.dto.AbstractDTO;
import com.nextech.erp.dto.QualityCheckRMDTO;
import com.nextech.erp.dto.RawMaterialInvoiceDTO;
import com.nextech.erp.dto.RawmaterialOrderDTO;

public class RMOrderHistoryDTO extends AbstractDTO {
	
	private String comment;
	private RawmaterialOrderDTO rawmaterialorder;
	private StatusDTO status1;
	private StatusDTO status2;
	private RawMaterialInvoiceDTO rawmaterialorderinvoice;
	private QualityCheckRMDTO qualitycheckrawmaterial;
	
	public RMOrderHistoryDTO(){
		
	}
	public RMOrderHistoryDTO(int id){
		this.setId(id);
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public RawmaterialOrderDTO getRawmaterialorder() {
		return rawmaterialorder;
	}
	public void setRawmaterialorder(RawmaterialOrderDTO rawmaterialorder) {
		this.rawmaterialorder = rawmaterialorder;
	}
	public StatusDTO getStatus1() {
		return status1;
	}
	public void setStatus1(StatusDTO status1) {
		this.status1 = status1;
	}
	public StatusDTO getStatus2() {
		return status2;
	}
	public void setStatus2(StatusDTO status2) {
		this.status2 = status2;
	}
	public RawMaterialInvoiceDTO getRawmaterialorderinvoice() {
		return rawmaterialorderinvoice;
	}
	public void setRawmaterialorderinvoice(
			RawMaterialInvoiceDTO rawmaterialorderinvoice) {
		this.rawmaterialorderinvoice = rawmaterialorderinvoice;
	}
	public QualityCheckRMDTO getQualitycheckrawmaterial() {
		return qualitycheckrawmaterial;
	}
	public void setQualitycheckrawmaterial(QualityCheckRMDTO qualitycheckrawmaterial) {
		this.qualitycheckrawmaterial = qualitycheckrawmaterial;
	}
	
}
