package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the rawmaterialorder database table.
 *
 */
@Entity
@NamedQuery(name="Rawmaterialorder.findAll", query="SELECT r FROM Rawmaterialorder r")
public class Rawmaterialorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="actual_price")
	private float actualPrice;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Temporal(TemporalType.DATE)
	private Date createDate;

	@Size(min = 4, max = 255, message = "{description sholud be greater than 4 or less than 255 characters}")
	private String description;

	@Temporal(TemporalType.DATE)
	@Column(name="expected_delivery_date")
	private Date expectedDeliveryDate;

	private boolean isactive;

	@Size(min = 2, max = 255, message = "{Rawmaterialorder sholud be greater than 2 or less than 255 characters}")
	private String name;

	@Column(name="other_charges")
	private float otherCharges;


	@Min(value = 1 ,message="Please enter atleast 1 quantity")
	private int quantity;


	private float tax;

	private float totalprice;

	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to Status
	@ManyToOne
	private Status status;

	//bi-directional many-to-one association to Vendor
	@ManyToOne
	private Vendor vendor;

	//bi-directional many-to-one association to Rawmaterialorderassociation
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rawmaterialorder")
	private List<Rawmaterialorderassociation> rawmaterialorderassociations;

	//bi-directional many-to-one association to Rawmaterialorderhistory
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rawmaterialorder", cascade = CascadeType.ALL)
	private List<Rawmaterialorderhistory> rawmaterialorderhistories;

	//bi-directional many-to-one association to Rawmaterialorderinvoiceassociation
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rawmaterialorder", cascade = CascadeType.ALL)
	private List<Rawmaterialorderinvoiceassociation> rawmaterialorderinvoiceassociations;

	public Rawmaterialorder() {
	}
	public Rawmaterialorder(int id) {
		this.id=id;
	}
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getActualPrice() {
		return this.actualPrice;
	}

	public void setActualPrice(float actualPrice) {
		this.actualPrice = actualPrice;
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

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getExpectedDeliveryDate() {
		return this.expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getOtherCharges() {
		return this.otherCharges;
	}

	public void setOtherCharges(float otherCharges) {
		this.otherCharges = otherCharges;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getTax() {
		return this.tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	public float getTotalprice() {
		return this.totalprice;
	}

	public void setTotalprice(float totalprice) {
		this.totalprice = totalprice;
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

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public List<Rawmaterialorderassociation> getRawmaterialorderassociations() {
		return this.rawmaterialorderassociations;
	}

	public void setRawmaterialorderassociations(List<Rawmaterialorderassociation> rawmaterialorderassociations) {
		this.rawmaterialorderassociations = rawmaterialorderassociations;
	}

	public Rawmaterialorderassociation addRawmaterialorderassociation(Rawmaterialorderassociation rawmaterialorderassociation) {
		getRawmaterialorderassociations().add(rawmaterialorderassociation);
		rawmaterialorderassociation.setRawmaterialorder(this);

		return rawmaterialorderassociation;
	}

	public Rawmaterialorderassociation removeRawmaterialorderassociation(Rawmaterialorderassociation rawmaterialorderassociation) {
		getRawmaterialorderassociations().remove(rawmaterialorderassociation);
		rawmaterialorderassociation.setRawmaterialorder(null);

		return rawmaterialorderassociation;
	}

	public List<Rawmaterialorderhistory> getRawmaterialorderhistories() {
		return this.rawmaterialorderhistories;
	}

	public void setRawmaterialorderhistories(List<Rawmaterialorderhistory> rawmaterialorderhistories) {
		this.rawmaterialorderhistories = rawmaterialorderhistories;
	}

	public Rawmaterialorderhistory addRawmaterialorderhistory(Rawmaterialorderhistory rawmaterialorderhistory) {
		getRawmaterialorderhistories().add(rawmaterialorderhistory);
		rawmaterialorderhistory.setRawmaterialorder(this);

		return rawmaterialorderhistory;
	}

	public Rawmaterialorderhistory removeRawmaterialorderhistory(Rawmaterialorderhistory rawmaterialorderhistory) {
		getRawmaterialorderhistories().remove(rawmaterialorderhistory);
		rawmaterialorderhistory.setRawmaterialorder(null);

		return rawmaterialorderhistory;
	}

	public List<Rawmaterialorderinvoiceassociation> getRawmaterialorderinvoiceassociations() {
		return this.rawmaterialorderinvoiceassociations;
	}

	public void setRawmaterialorderinvoiceassociations(List<Rawmaterialorderinvoiceassociation> rawmaterialorderinvoiceassociations) {
		this.rawmaterialorderinvoiceassociations = rawmaterialorderinvoiceassociations;
	}

	public Rawmaterialorderinvoiceassociation addRawmaterialorderinvoiceassociation(Rawmaterialorderinvoiceassociation rawmaterialorderinvoiceassociation) {
		getRawmaterialorderinvoiceassociations().add(rawmaterialorderinvoiceassociation);
		rawmaterialorderinvoiceassociation.setRawmaterialorder(this);

		return rawmaterialorderinvoiceassociation;
	}

	public Rawmaterialorderinvoiceassociation removeRawmaterialorderinvoiceassociation(Rawmaterialorderinvoiceassociation rawmaterialorderinvoiceassociation) {
		getRawmaterialorderinvoiceassociations().remove(rawmaterialorderinvoiceassociation);
		rawmaterialorderinvoiceassociation.setRawmaterialorder(null);

		return rawmaterialorderinvoiceassociation;
	}


}