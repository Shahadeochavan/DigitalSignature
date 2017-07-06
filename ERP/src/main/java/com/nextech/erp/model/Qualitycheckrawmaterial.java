package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the qualitycheckrawmaterial database table.
 * 
 */
@Entity
@NamedQuery(name="Qualitycheckrawmaterial.findAll", query="SELECT q FROM Qualitycheckrawmaterial q")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Qualitycheckrawmaterial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	private int goodQuantity;

	private int intakeQuantity;

	private boolean isactive;

	private boolean isReturnInvoiceInitated;

	private String remark;

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

	//bi-directional many-to-one association to Rawmaterialinventoryhistory
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "qualitycheckrawmaterial", cascade = CascadeType.ALL)
	private List<Rawmaterialinventoryhistory> rawmaterialinventoryhistories;

	//bi-directional many-to-one association to Rawmaterialorderhistory
	@OneToMany(mappedBy="qualitycheckrawmaterial")
/*	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "qualitycheckrawmaterial", cascade = CascadeType.ALL)*/
	private List<Rawmaterialorderhistory> rawmaterialorderhistories;

	public Qualitycheckrawmaterial() {
	}
	public Qualitycheckrawmaterial(int id) {
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

	public int getGoodQuantity() {
		return this.goodQuantity;
	}

	public void setGoodQuantity(int goodQuantity) {
		this.goodQuantity = goodQuantity;
	}

	public int getIntakeQuantity() {
		return this.intakeQuantity;
	}

	public void setIntakeQuantity(int intakeQuantity) {
		this.intakeQuantity = intakeQuantity;
	}

	public boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public boolean getIsReturnInvoiceInitated() {
		return this.isReturnInvoiceInitated;
	}

	public void setIsReturnInvoiceInitated(boolean isReturnInvoiceInitated) {
		this.isReturnInvoiceInitated = isReturnInvoiceInitated;
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

	public List<Rawmaterialinventoryhistory> getRawmaterialinventoryhistories() {
		return this.rawmaterialinventoryhistories;
	}

	public void setRawmaterialinventoryhistories(List<Rawmaterialinventoryhistory> rawmaterialinventoryhistories) {
		this.rawmaterialinventoryhistories = rawmaterialinventoryhistories;
	}

	public Rawmaterialinventoryhistory addRawmaterialinventoryhistory(Rawmaterialinventoryhistory rawmaterialinventoryhistory) {
		getRawmaterialinventoryhistories().add(rawmaterialinventoryhistory);
		rawmaterialinventoryhistory.setQualitycheckrawmaterial(this);

		return rawmaterialinventoryhistory;
	}

	public Rawmaterialinventoryhistory removeRawmaterialinventoryhistory(Rawmaterialinventoryhistory rawmaterialinventoryhistory) {
		getRawmaterialinventoryhistories().remove(rawmaterialinventoryhistory);
		rawmaterialinventoryhistory.setQualitycheckrawmaterial(null);

		return rawmaterialinventoryhistory;
	}

	public List<Rawmaterialorderhistory> getRawmaterialorderhistories() {
		return this.rawmaterialorderhistories;
	}

	public void setRawmaterialorderhistories(List<Rawmaterialorderhistory> rawmaterialorderhistories) {
		this.rawmaterialorderhistories = rawmaterialorderhistories;
	}

	public Rawmaterialorderhistory addRawmaterialorderhistory(Rawmaterialorderhistory rawmaterialorderhistory) {
		getRawmaterialorderhistories().add(rawmaterialorderhistory);
		rawmaterialorderhistory.setQualitycheckrawmaterial(this);

		return rawmaterialorderhistory;
	}

	public Rawmaterialorderhistory removeRawmaterialorderhistory(Rawmaterialorderhistory rawmaterialorderhistory) {
		getRawmaterialorderhistories().remove(rawmaterialorderhistory);
		rawmaterialorderhistory.setQualitycheckrawmaterial(null);

		return rawmaterialorderhistory;
	}

}