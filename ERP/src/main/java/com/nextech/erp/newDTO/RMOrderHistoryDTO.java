package com.nextech.erp.newDTO;


import com.nextech.erp.dto.AbstractDTO;
import com.nextech.erp.model.Qualitycheckrawmaterial;
import com.nextech.erp.model.Rawmaterialorder;
import com.nextech.erp.model.Rawmaterialorderinvoice;
import com.nextech.erp.model.Status;

public class RMOrderHistoryDTO extends AbstractDTO {
	
	private String comment;
	private Rawmaterialorder rawmaterialorder;
	private Status status1;
	private Status status2;
	private Rawmaterialorderinvoice rawmaterialorderinvoice;
	private Qualitycheckrawmaterial qualitycheckrawmaterial;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Rawmaterialorder getRawmaterialorder() {
		return rawmaterialorder;
	}
	public void setRawmaterialorder(Rawmaterialorder rawmaterialorder) {
		this.rawmaterialorder = rawmaterialorder;
	}
	public Status getStatus1() {
		return status1;
	}
	public void setStatus1(Status status1) {
		this.status1 = status1;
	}
	public Status getStatus2() {
		return status2;
	}
	public void setStatus2(Status status2) {
		this.status2 = status2;
	}
	public Rawmaterialorderinvoice getRawmaterialorderinvoice() {
		return rawmaterialorderinvoice;
	}
	public void setRawmaterialorderinvoice(
			Rawmaterialorderinvoice rawmaterialorderinvoice) {
		this.rawmaterialorderinvoice = rawmaterialorderinvoice;
	}
	public Qualitycheckrawmaterial getQualitycheckrawmaterial() {
		return qualitycheckrawmaterial;
	}
	public void setQualitycheckrawmaterial(
			Qualitycheckrawmaterial qualitycheckrawmaterial) {
		this.qualitycheckrawmaterial = qualitycheckrawmaterial;
	}
	

}
