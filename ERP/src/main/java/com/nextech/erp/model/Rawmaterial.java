package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the rawmaterial database table.
 *
 */
@Entity
@NamedQuery(name="Rawmaterial.findAll", query="SELECT r FROM Rawmaterial r")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rawmaterial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;


	@Size(min = 4, max = 255, message = "{description sholud be greater than 4 or less than 255 characters}")
	private String description;

	private boolean isactive;

	@Size(min = 2, max = 255, message = "{Rawmaterial name sholud be greater than 2 or less than 255 characters}")
	private String name;

	@Size(min = 2, max = 255, message = "{Rawmaterial Part Number  sholud be greater than 2 or less than 255 characters or digits}")
	@Column(name="part_number")
	private String partNumber;

	@Column(name="price_per_unit")
	private float pricePerUnit;

	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rawmaterial", cascade = CascadeType.ALL)
	private List<Bomrmvendorassociation> bomrmvendorassociations;

	//bi-directional many-to-one association to Productrawmaterialassociation
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rawmaterial", cascade = CascadeType.ALL)
	private List<Productrawmaterialassociation> productrawmaterialassociations;

	//bi-directional many-to-one association to Qualitycheckrawmaterial
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rawmaterial", cascade = CascadeType.ALL)
	private List<Qualitycheckrawmaterial> qualitycheckrawmaterials;

	//bi-directional many-to-one association to Unit
	@ManyToOne
	private Unit unit;

	//bi-directional many-to-one association to Rawmaterialinventory
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rawmaterial", cascade = CascadeType.ALL)
	private List<Rawmaterialinventory> rawmaterialinventories;

	//bi-directional many-to-one association to Rawmaterialorderassociation
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rawmaterial", cascade = CascadeType.ALL)
	private List<Rawmaterialorderassociation> rawmaterialorderassociations;

	//bi-directional many-to-one association to Rawmaterialvendorassociation
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rawmaterial", cascade = CascadeType.ALL)
	private List<Rawmaterialvendorassociation> rawmaterialvendorassociations;

	//bi-directional many-to-one association to Rmorderinvoiceintakquantity
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rawmaterial", cascade = CascadeType.ALL)
	private List<Rmorderinvoiceintakquantity> rmorderinvoiceintakquantities;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rawmaterial", cascade = CascadeType.ALL)
	private List<Storeoutrm> storeoutrms;

	public Rawmaterial() {
	}
	public Rawmaterial(int id) {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPartNumber() {
		return this.partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
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

	public List<Productrawmaterialassociation> getProductrawmaterialassociations() {
		return this.productrawmaterialassociations;
	}

	public void setProductrawmaterialassociations(List<Productrawmaterialassociation> productrawmaterialassociations) {
		this.productrawmaterialassociations = productrawmaterialassociations;
	}

	public Productrawmaterialassociation addProductrawmaterialassociation(Productrawmaterialassociation productrawmaterialassociation) {
		getProductrawmaterialassociations().add(productrawmaterialassociation);
		productrawmaterialassociation.setRawmaterial(this);

		return productrawmaterialassociation;
	}

	public Productrawmaterialassociation removeProductrawmaterialassociation(Productrawmaterialassociation productrawmaterialassociation) {
		getProductrawmaterialassociations().remove(productrawmaterialassociation);
		productrawmaterialassociation.setRawmaterial(null);

		return productrawmaterialassociation;
	}

	public List<Qualitycheckrawmaterial> getQualitycheckrawmaterials() {
		return this.qualitycheckrawmaterials;
	}

	public void setQualitycheckrawmaterials(List<Qualitycheckrawmaterial> qualitycheckrawmaterials) {
		this.qualitycheckrawmaterials = qualitycheckrawmaterials;
	}

	public Qualitycheckrawmaterial addQualitycheckrawmaterial(Qualitycheckrawmaterial qualitycheckrawmaterial) {
		getQualitycheckrawmaterials().add(qualitycheckrawmaterial);
		qualitycheckrawmaterial.setRawmaterial(this);

		return qualitycheckrawmaterial;
	}

	public Qualitycheckrawmaterial removeQualitycheckrawmaterial(Qualitycheckrawmaterial qualitycheckrawmaterial) {
		getQualitycheckrawmaterials().remove(qualitycheckrawmaterial);
		qualitycheckrawmaterial.setRawmaterial(null);

		return qualitycheckrawmaterial;
	}

	public Unit getUnit() {
		return this.unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public List<Rawmaterialinventory> getRawmaterialinventories() {
		return this.rawmaterialinventories;
	}

	public void setRawmaterialinventories(List<Rawmaterialinventory> rawmaterialinventories) {
		this.rawmaterialinventories = rawmaterialinventories;
	}

	public Rawmaterialinventory addRawmaterialinventory(Rawmaterialinventory rawmaterialinventory) {
		getRawmaterialinventories().add(rawmaterialinventory);
		rawmaterialinventory.setRawmaterial(this);

		return rawmaterialinventory;
	}

	public Rawmaterialinventory removeRawmaterialinventory(Rawmaterialinventory rawmaterialinventory) {
		getRawmaterialinventories().remove(rawmaterialinventory);
		rawmaterialinventory.setRawmaterial(null);

		return rawmaterialinventory;
	}

	public List<Rawmaterialorderassociation> getRawmaterialorderassociations() {
		return this.rawmaterialorderassociations;
	}

	public void setRawmaterialorderassociations(List<Rawmaterialorderassociation> rawmaterialorderassociations) {
		this.rawmaterialorderassociations = rawmaterialorderassociations;
	}

	public Rawmaterialorderassociation addRawmaterialorderassociation(Rawmaterialorderassociation rawmaterialorderassociation) {
		getRawmaterialorderassociations().add(rawmaterialorderassociation);
		rawmaterialorderassociation.setRawmaterial(this);

		return rawmaterialorderassociation;
	}

	public Rawmaterialorderassociation removeRawmaterialorderassociation(Rawmaterialorderassociation rawmaterialorderassociation) {
		getRawmaterialorderassociations().remove(rawmaterialorderassociation);
		rawmaterialorderassociation.setRawmaterial(null);

		return rawmaterialorderassociation;
	}

	public List<Rawmaterialvendorassociation> getRawmaterialvendorassociations() {
		return this.rawmaterialvendorassociations;
	}

	public void setRawmaterialvendorassociations(List<Rawmaterialvendorassociation> rawmaterialvendorassociations) {
		this.rawmaterialvendorassociations = rawmaterialvendorassociations;
	}

	public Rawmaterialvendorassociation addRawmaterialvendorassociation(Rawmaterialvendorassociation rawmaterialvendorassociation) {
		getRawmaterialvendorassociations().add(rawmaterialvendorassociation);
		rawmaterialvendorassociation.setRawmaterial(this);

		return rawmaterialvendorassociation;
	}

	public Rawmaterialvendorassociation removeRawmaterialvendorassociation(Rawmaterialvendorassociation rawmaterialvendorassociation) {
		getRawmaterialvendorassociations().remove(rawmaterialvendorassociation);
		rawmaterialvendorassociation.setRawmaterial(null);

		return rawmaterialvendorassociation;
	}

	public List<Rmorderinvoiceintakquantity> getRmorderinvoiceintakquantities() {
		return this.rmorderinvoiceintakquantities;
	}

	public void setRmorderinvoiceintakquantities(List<Rmorderinvoiceintakquantity> rmorderinvoiceintakquantities) {
		this.rmorderinvoiceintakquantities = rmorderinvoiceintakquantities;
	}

	public Rmorderinvoiceintakquantity addRmorderinvoiceintakquantity(Rmorderinvoiceintakquantity rmorderinvoiceintakquantity) {
		getRmorderinvoiceintakquantities().add(rmorderinvoiceintakquantity);
		rmorderinvoiceintakquantity.setRawmaterial(this);

		return rmorderinvoiceintakquantity;
	}

	public Rmorderinvoiceintakquantity removeRmorderinvoiceintakquantity(Rmorderinvoiceintakquantity rmorderinvoiceintakquantity) {
		getRmorderinvoiceintakquantities().remove(rmorderinvoiceintakquantity);
		rmorderinvoiceintakquantity.setRawmaterial(null);

		return rmorderinvoiceintakquantity;
	}
	public List<Storeoutrm> getStoreoutrms() {
		return this.storeoutrms;
	}

	public void setStoreoutrms(List<Storeoutrm> storeoutrms) {
		this.storeoutrms = storeoutrms;
	}

	public Storeoutrm addStoreoutrm(Storeoutrm storeoutrm) {
		getStoreoutrms().add(storeoutrm);
		storeoutrm.setRawmaterial(this);

		return storeoutrm;
	}

	public Storeoutrm removeStoreoutrm(Storeoutrm storeoutrm) {
		getStoreoutrms().remove(storeoutrm);
		storeoutrm.setRawmaterial(null);

		return storeoutrm;
	}
	public List<Bomrmvendorassociation> getBomrmvendorassociations() {
		return this.bomrmvendorassociations;
	}

	public void setBomrmvendorassociations(List<Bomrmvendorassociation> bomrmvendorassociations) {
		this.bomrmvendorassociations = bomrmvendorassociations;
	}

	public Bomrmvendorassociation addBomrmvendorassociation(Bomrmvendorassociation bomrmvendorassociation) {
		getBomrmvendorassociations().add(bomrmvendorassociation);
		bomrmvendorassociation.setRawmaterial(this);

		return bomrmvendorassociation;
	}

	public Bomrmvendorassociation removeBomrmvendorassociation(Bomrmvendorassociation bomrmvendorassociation) {
		getBomrmvendorassociations().remove(bomrmvendorassociation);
		bomrmvendorassociation.setRawmaterial(null);

		return bomrmvendorassociation;
	}
}