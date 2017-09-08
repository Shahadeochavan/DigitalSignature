package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the taxstructure database table.
 * 
 */
@Entity
@NamedQuery(name="Taxstructure.findAll", query="SELECT t FROM Taxstructure t")
public class Taxstructure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private double cgst;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	private String description;

	private double igst;

	private boolean isactive;

	private double other1;

	private double other2;

	private double sgst;

	@Column(name="updated_by")
	private int updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to Product
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "taxstructure", cascade = CascadeType.ALL)
	private List<Product> products;

	//bi-directional many-to-one association to Rawmaterialvendorassociation
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "taxstructure", cascade = CascadeType.ALL)
	private List<Rawmaterialvendorassociation> rawmaterialvendorassociations;

	public Taxstructure() {
	}
	
	public Taxstructure(int id) {
		this.id=id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getCgst() {
		return this.cgst;
	}

	public void setCgst(double cgst) {
		this.cgst = cgst;
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

	public double getIgst() {
		return this.igst;
	}

	public void setIgst(double igst) {
		this.igst = igst;
	}

	public boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public double getOther1() {
		return this.other1;
	}

	public void setOther1(double other1) {
		this.other1 = other1;
	}

	public double getOther2() {
		return this.other2;
	}

	public void setOther2(double other2) {
		this.other2 = other2;
	}

	public double getSgst() {
		return this.sgst;
	}

	public void setSgst(double sgst) {
		this.sgst = sgst;
	}

	public int getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setTaxstructure(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setTaxstructure(null);

		return product;
	}

	public List<Rawmaterialvendorassociation> getRawmaterialvendorassociations() {
		return this.rawmaterialvendorassociations;
	}

	public void setRawmaterialvendorassociations(List<Rawmaterialvendorassociation> rawmaterialvendorassociations) {
		this.rawmaterialvendorassociations = rawmaterialvendorassociations;
	}

	public Rawmaterialvendorassociation addRawmaterialvendorassociation(Rawmaterialvendorassociation rawmaterialvendorassociation) {
		getRawmaterialvendorassociations().add(rawmaterialvendorassociation);
		rawmaterialvendorassociation.setTaxstructure(this);

		return rawmaterialvendorassociation;
	}

	public Rawmaterialvendorassociation removeRawmaterialvendorassociation(Rawmaterialvendorassociation rawmaterialvendorassociation) {
		getRawmaterialvendorassociations().remove(rawmaterialvendorassociation);
		rawmaterialvendorassociation.setTaxstructure(null);

		return rawmaterialvendorassociation;
	}

}