package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;


/**
 * The persistent class for the productinventoryhistory database table.
 *
 */
@Entity
@NamedQuery(name="Productinventoryhistory.findAll", query="SELECT p FROM Productinventoryhistory p")
public class Productinventoryhistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	// @Min(value = 0, message = " please enter after quantity")
	private long afterquantity;

	// @Min(value = 0, message = " please enter before quantity")
	private long beforequantity;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Size(min = 4, max = 255, message = "{description sholud be greater than 4 or less than 255 characters}")
	private String description;

	private boolean isactive;

	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to Productinventory
	@ManyToOne
	@JoinColumn(name="productinventoryid")
	private Productinventory productinventory;

	//bi-directional many-to-one association to Status
	@ManyToOne
	@JoinColumn(name="statusid")
	private Status status;

	public Productinventoryhistory() {
	}
	public Productinventoryhistory(int id) {
		this.id=id;
	}


	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAfterquantity() {
		return this.afterquantity;
	}

	public void setAfterquantity(long afterquantity) {
		this.afterquantity = afterquantity;
	}

	public long getBeforequantity() {
		return this.beforequantity;
	}

	public void setBeforequantity(long beforequantity) {
		this.beforequantity = beforequantity;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
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

	public Productinventory getProductinventory() {
		return this.productinventory;
	}

	public void setProductinventory(Productinventory productinventory) {
		this.productinventory = productinventory;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}