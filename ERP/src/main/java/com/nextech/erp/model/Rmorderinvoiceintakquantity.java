package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;


/**
 * The persistent class for the rmorderinvoiceintakquantity database table.
 * 
 */
@Entity
@NamedQuery(name="Rmorderinvoiceintakquantity.findAll", query="SELECT r FROM Rmorderinvoiceintakquantity r")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rmorderinvoiceintakquantity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	private boolean isactive;

	private int quantity;

	private int shortquantity;

	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to Rawmaterial
	@ManyToOne
	@JoinColumn(name="rawmaterialid")
	private Rawmaterial rawmaterial;

	//bi-directional many-to-one association to Rawmaterialorderinvoice
	@ManyToOne
	@JoinColumn(name="rmorderinvoiceid")	
	private Rawmaterialorderinvoice rawmaterialorderinvoice;

	public Rmorderinvoiceintakquantity() {
	}
	public Rmorderinvoiceintakquantity(int id) {
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

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getShortquantity() {
		return this.shortquantity;
	}

	public void setShortquantity(int shortquantity) {
		this.shortquantity = shortquantity;
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
	   @JsonIgnore
	    @JsonProperty(value = "rmorderinvoiceid")
	public Rawmaterialorderinvoice getRawmaterialorderinvoice() {
		return this.rawmaterialorderinvoice;
	}

	public void setRawmaterialorderinvoice(Rawmaterialorderinvoice rawmaterialorderinvoice) {
		this.rawmaterialorderinvoice = rawmaterialorderinvoice;
	}

}