package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the rmtype database table.
 * 
 */
@Entity
@NamedQuery(name="Rmtype.findAll", query="SELECT r FROM Rmtype r")
public class Rmtype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	private String description;

	private boolean  isactive;

	private String name;

	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rmtype", cascade = CascadeType.ALL)
	private List<Rawmaterial> rawmaterials;

	public Rmtype() {
	}
	
	public Rmtype(int id) {
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
	public List<Rawmaterial> getRawmaterials() {
		return this.rawmaterials;
	}

	public void setRawmaterials(List<Rawmaterial> rawmaterials) {
		this.rawmaterials = rawmaterials;
	}

	public Rawmaterial addRawmaterial(Rawmaterial rawmaterial) {
		getRawmaterials().add(rawmaterial);
		rawmaterial.setRmtype(this);

		return rawmaterial;
	}

	public Rawmaterial removeRawmaterial(Rawmaterial rawmaterial) {
		getRawmaterials().remove(rawmaterial);
		rawmaterial.setRmtype(null);

		return rawmaterial;
	}
}