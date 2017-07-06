package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the dailyproduction database table.
 * 
 */
@Entity
@NamedQuery(name="Dailyproduction.findAll", query="SELECT d FROM Dailyproduction d")
public class Dailyproduction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;

	@Column(name="achived_quantity")
	private int achivedQuantity;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	private boolean isactive;

	private String remark;

	@Column(name="target_quantity")
	private int targetQuantity;
	
	private long repaired_quantity;

	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to Productionplanning
	@ManyToOne
	@JoinColumn(name="productionPlanid")
	private Productionplanning productionplanning;

	//bi-directional many-to-one association to Status
	@ManyToOne
	private Status status;

	public Dailyproduction() {
	}
	public Dailyproduction(int id) {
		this.id=id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAchivedQuantity() {
		return this.achivedQuantity;
	}

	public void setAchivedQuantity(int achivedQuantity) {
		this.achivedQuantity = achivedQuantity;
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getTargetQuantity() {
		return this.targetQuantity;
	}

	public void setTargetQuantity(int targetQuantity) {
		this.targetQuantity = targetQuantity;
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

	public Productionplanning getProductionplanning() {
		return this.productionplanning;
	}

	public void setProductionplanning(Productionplanning productionplanning) {
		this.productionplanning = productionplanning;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	public long getRepaired_quantity() {
		return repaired_quantity;
	}
	public void setRepaired_quantity(long repaired_quantity) {
		this.repaired_quantity = repaired_quantity;
	}

}