package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the rawmaterialinventory database table.
 * 
 */
@Entity
@NamedQuery(name="Rawmaterialinventory.findAll", query="SELECT r FROM Rawmaterialinventory r")
public class Rawmaterialinventory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;
	
	private long minimum_quantity;
	private long maximum_quantity;

	private boolean isactive;

	private long quantityAvailable;

/*	@NotBlank(message="{rack number should not be blank}")
	@Size(min = 1, max = 255, message = "{rack number sholud be greater than 1 or less than 255 characters or digits}")*/
	private Integer racknumber;

	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to Rawmaterial
	@ManyToOne
	private Rawmaterial rawmaterial;

	//bi-directional many-to-one association to Rawmaterialinventoryhistory
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rawmaterialinventory", cascade = CascadeType.ALL)
	private List<Rawmaterialinventoryhistory> rawmaterialinventoryhistories;

	public Rawmaterialinventory() {
	}
	public Rawmaterialinventory(int id) {
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

	public long getMinimum_quantity() {
		return minimum_quantity;
	}
	public void setMinimum_quantity(long minimum_quantity) {
		this.minimum_quantity = minimum_quantity;
	}
	public long getMaximum_quantity() {
		return maximum_quantity;
	}
	public void setMaximum_quantity(long maximum_quantity) {
		this.maximum_quantity = maximum_quantity;
	}
	public long getQuantityAvailable() {
		return this.quantityAvailable;
	}

	public void setQuantityAvailable(long quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}

	public Integer getRacknumber() {
		return this.racknumber;
	}

	public void setRacknumber(Integer racknumber) {
		this.racknumber = racknumber;
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

	public List<Rawmaterialinventoryhistory> getRawmaterialinventoryhistories() {
		return this.rawmaterialinventoryhistories;
	}

	public void setRawmaterialinventoryhistories(List<Rawmaterialinventoryhistory> rawmaterialinventoryhistories) {
		this.rawmaterialinventoryhistories = rawmaterialinventoryhistories;
	}

	public Rawmaterialinventoryhistory addRawmaterialinventoryhistory(Rawmaterialinventoryhistory rawmaterialinventoryhistory) {
		getRawmaterialinventoryhistories().add(rawmaterialinventoryhistory);
		rawmaterialinventoryhistory.setRawmaterialinventory(this);

		return rawmaterialinventoryhistory;
	}

	public Rawmaterialinventoryhistory removeRawmaterialinventoryhistory(Rawmaterialinventoryhistory rawmaterialinventoryhistory) {
		getRawmaterialinventoryhistories().remove(rawmaterialinventoryhistory);
		rawmaterialinventoryhistory.setRawmaterialinventory(null);

		return rawmaterialinventoryhistory;
	}

}