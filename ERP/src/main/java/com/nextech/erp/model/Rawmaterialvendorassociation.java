package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the rawmaterialvendorassociation database table.
 * 
 */
@Entity
@NamedQuery(name="Rawmaterialvendorassociation.findAll", query="SELECT r FROM Rawmaterialvendorassociation r")
public class Rawmaterialvendorassociation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	private boolean isactive;

/*	@DecimalMax(value = "100.00", message = "The pricePerUnit value can not be more than 100.00 ")
	@DecimalMin(value = "1.00", message = "The pricePerUnit value can not be less than 1.00 digit ")*/
	@Column(name="price_per_unit")
	private float pricePerUnit;

	
	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to Rawmaterial
	@ManyToOne
	private Rawmaterial rawmaterial;

	//bi-directional many-to-one association to Vendor
	@ManyToOne
	private Vendor vendor;

	public Rawmaterialvendorassociation() {
	}
	public Rawmaterialvendorassociation(int id) {
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

	public boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public float getPricePerUnit() {
		return this.pricePerUnit;
	}

	public void setPricePerUnit(float pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
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

	public Rawmaterial getRawmaterial() {
		return this.rawmaterial;
	}

	public void setRawmaterial(Rawmaterial rawmaterial) {
		this.rawmaterial = rawmaterial;
	}

	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

}