package com.nextech.dscrm.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.sql.Timestamp;


/**
 * The persistent class for the qualitycheckguidelines database table.
 * 
 */
@Entity
@Table(name="qualitycheckguidelines")
@NamedQuery(name="Qualitycheckguideline.findAll", query="SELECT q FROM Qualitycheckguideline q")
public class Qualitycheckguideline implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Size(min = 10, max = 2000, message = "{guidelines sholud be greater than 10 or less than 2000 characters}")
	private String guidelines;

	private boolean isactive;

	private long productId;

	private long rawMaterialId;

	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	public Qualitycheckguideline() {
	}
	
	public Qualitycheckguideline(int id) {
		this.id=id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getGuidelines() {
		return this.guidelines;
	}

	public void setGuidelines(String guidelines) {
		this.guidelines = guidelines;
	}

	public boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public long getProductId() {
		return this.productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getRawMaterialId() {
		return this.rawMaterialId;
	}

	public void setRawMaterialId(long rawMaterialId) {
		this.rawMaterialId = rawMaterialId;
	}

	public long getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

}