package com.nextech.dscrm.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Size;




import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the product database table.
 *
 */
@Entity
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="product_type")
	private String productType;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Size(min = 2, max = 255, message = "{Description sholud be greater than 4 or less than 255 characters}")
	private String description;

	private String productValidity;

	private boolean isactive;
	
	private float pricePerUnit;

	@Size(min = 2, max = 255, message = "{Name sholud be greater than 2 or less than 255 characters}")
	private String name;

	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private List<Clientproductasso> clientproductassos;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private List<Applicant> applicants;


	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private List<Productinventory> productinventories;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private List<Productorderassociation> productorderassociations;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
	private List<Productorder> productorders;

	public Product() {
	}

	public Product(int id) {
		this.id=id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
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

	public List<Productinventory> getProductinventories() {
		return this.productinventories;
	}

	public void setProductinventories(List<Productinventory> productinventories) {
		this.productinventories = productinventories;
	}

	public Productinventory addProductinventory(Productinventory productinventory) {
		getProductinventories().add(productinventory);
		productinventory.setProduct(this);

		return productinventory;
	}

	public Productinventory removeProductinventory(Productinventory productinventory) {
		getProductinventories().remove(productinventory);
		productinventory.setProduct(null);

		return productinventory;
	}

	public List<Productorderassociation> getProductorderassociations() {
		return this.productorderassociations;
	}

	public void setProductorderassociations(List<Productorderassociation> productorderassociations) {
		this.productorderassociations = productorderassociations;
	}

	public Productorderassociation addProductorderassociation(Productorderassociation productorderassociation) {
		getProductorderassociations().add(productorderassociation);
		productorderassociation.setProduct(this);

		return productorderassociation;
	}

	public Productorderassociation removeProductorderassociation(Productorderassociation productorderassociation) {
		getProductorderassociations().remove(productorderassociation);
		productorderassociation.setProduct(null);

		return productorderassociation;
	}

	public float getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(float pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public List<Clientproductasso> getClientproductassos() {
		return clientproductassos;
	}

	public void setClientproductassos(List<Clientproductasso> clientproductassos) {
		this.clientproductassos = clientproductassos;
	}

	public List<Applicant> getApplicants() {
		return applicants;
	}

	public void setApplicants(List<Applicant> applicants) {
		this.applicants = applicants;
	}

	public String getProductValidity() {
		return productValidity;
	}

	public void setProductValidity(String productValidity) {
		this.productValidity = productValidity;
	}

	public List<Productorder> getProductorders() {
		return productorders;
	}

	public void setProductorders(List<Productorder> productorders) {
		this.productorders = productorders;
	}
	
}