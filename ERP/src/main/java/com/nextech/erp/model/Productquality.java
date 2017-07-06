package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the productquality database table.
 * 
 */
@Entity
@NamedQuery(name="Productquality.findAll", query="SELECT p FROM Productquality p")
public class Productquality implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;

	private long checkQuantity;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	private long goodQuantity;

	private boolean isactive;

	private long rejectedQuantity;

	private String remark;

	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="productid")
	private Product product;

	//bi-directional many-to-one association to Productionplanning
	@ManyToOne
	@JoinColumn(name="productionPlanid")
	private Productionplanning productionplanning;

	public Productquality() {
	}
	public Productquality(int id) {
		this.id=id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCheckQuantity() {
		return this.checkQuantity;
	}

	public void setCheckQuantity(long checkQuantity) {
		this.checkQuantity = checkQuantity;
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

	public long getGoodQuantity() {
		return this.goodQuantity;
	}

	public void setGoodQuantity(long goodQuantity) {
		this.goodQuantity = goodQuantity;
	}

	public boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public long getRejectedQuantity() {
		return this.rejectedQuantity;
	}

	public void setRejectedQuantity(long rejectedQuantity) {
		this.rejectedQuantity = rejectedQuantity;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Productionplanning getProductionplanning() {
		return this.productionplanning;
	}

	public void setProductionplanning(Productionplanning productionplanning) {
		this.productionplanning = productionplanning;
	}

}