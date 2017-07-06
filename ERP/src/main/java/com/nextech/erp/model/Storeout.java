package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the storeout database table.
 * 
 */
@Entity
@NamedQuery(name="Storeout.findAll", query="SELECT s FROM Storeout s")
public class Storeout implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	private String description;

	private boolean isactive;
	
	private boolean isSelectedStoreOut;

	private long quantityRequired;

	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="productId")
	private Product product;

	//bi-directional many-to-one association to Productionplanning
	@ManyToOne
	@JoinColumn(name="productionPlanid")
	private Productionplanning productionplanning;

	//bi-directional many-to-one association to Status
	@ManyToOne
	private Status status;

	//bi-directional many-to-one association to Storeoutrmassociation
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "storeout", cascade = CascadeType.ALL)
	private List<Storeoutrmassociation> storeoutrmassociations;

	public Storeout() {
	}
	
	public Storeout(int id) {
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

	
	public boolean isSelectedStoreOut() {
		return isSelectedStoreOut;
	}

	public void setSelectedStoreOut(boolean isSelectedStoreOut) {
		this.isSelectedStoreOut = isSelectedStoreOut;
	}

	public long getQuantityRequired() {
		return this.quantityRequired;
	}

	public void setQuantityRequired(long quantityRequired) {
		this.quantityRequired = quantityRequired;
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

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Storeoutrmassociation> getStoreoutrmassociations() {
		return this.storeoutrmassociations;
	}

	public void setStoreoutrmassociations(List<Storeoutrmassociation> storeoutrmassociations) {
		this.storeoutrmassociations = storeoutrmassociations;
	}

	public Storeoutrmassociation addStoreoutrmassociation(Storeoutrmassociation storeoutrmassociation) {
		getStoreoutrmassociations().add(storeoutrmassociation);
		storeoutrmassociation.setStoreout(this);

		return storeoutrmassociation;
	}

	public Storeoutrmassociation removeStoreoutrmassociation(Storeoutrmassociation storeoutrmassociation) {
		getStoreoutrmassociations().remove(storeoutrmassociation);
		storeoutrmassociation.setStoreout(null);

		return storeoutrmassociation;
	}

}