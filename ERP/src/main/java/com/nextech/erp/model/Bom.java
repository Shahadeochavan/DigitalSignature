package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the bom database table.
 * 
 */
@Entity
@NamedQuery(name="Bom.findAll", query="SELECT b FROM Bom b")
public class Bom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;

	@Column(name="bom_id")
	private String bomId;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	private boolean isactive;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="productid")
	private Product product;

	//bi-directional many-to-one association to Bomrmvendorassociation
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bom", cascade = CascadeType.ALL)
	private List<Bomrmvendorassociation> bomrmvendorassociations;

	public Bom() {
	}
	
	public Bom(int id) {
		this.id=id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBomId() {
		return this.bomId;
	}

	public void setBomId(String bomId) {
		this.bomId = bomId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
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

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
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

	public List<Bomrmvendorassociation> getBomrmvendorassociations() {
		return this.bomrmvendorassociations;
	}

	public void setBomrmvendorassociations(List<Bomrmvendorassociation> bomrmvendorassociations) {
		this.bomrmvendorassociations = bomrmvendorassociations;
	}

	public Bomrmvendorassociation addBomrmvendorassociation(Bomrmvendorassociation bomrmvendorassociation) {
		getBomrmvendorassociations().add(bomrmvendorassociation);
		bomrmvendorassociation.setBom(this);

		return bomrmvendorassociation;
	}

	public Bomrmvendorassociation removeBomrmvendorassociation(Bomrmvendorassociation bomrmvendorassociation) {
		getBomrmvendorassociations().remove(bomrmvendorassociation);
		bomrmvendorassociation.setBom(null);

		return bomrmvendorassociation;
	}

}