package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.sql.Timestamp;

/**
 * The persistent class for the dispatch database table.
 *
 */
@Entity
@NamedQuery(name = "Dispatch.findAll", query = "SELECT d FROM Dispatch d")
public class Dispatch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "created_by")
	private long createdBy;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Size(min = 2, max = 255, message = "{Invoice Number sholud be greater than 2 or less than 255 characters or digits}")
	private String invoiceNo;

	@Size(min = 2, max = 255, message = "{Description sholud be greater than 2 or less than 255 characters}")
	private String description;

	private boolean isactive;

	private long quantity;

	@Column(name = "updated_by")
	private long updatedBy;

	@Column(name = "updated_date")
	private Timestamp updatedDate;

	// bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name = "productid")
	private Product product;

	// bi-directional many-to-one association to Productorder
	@ManyToOne
	@JoinColumn(name = "product_orderid")
	private Productorder productorder;

	// bi-directional many-to-one association to Status
	@ManyToOne
	@JoinColumn(name = "statusId")
	private Status status;

	public Dispatch() {
	}

	public Dispatch(int id) {
		this.id = id;
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

	public long getQuantity() {
		return this.quantity;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
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

	public Productorder getProductorder() {
		return this.productorder;
	}

	public void setProductorder(Productorder productorder) {
		this.productorder = productorder;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}