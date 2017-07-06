package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the storeoutrmassociation database table.
 * 
 */
@Entity
@NamedQuery(name="Storeoutrmassociation.findAll", query="SELECT s FROM Storeoutrmassociation s")
public class Storeoutrmassociation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	private boolean isactive;

	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to Storeout
	@ManyToOne
	@JoinColumn(name="storeOutId")
	private Storeout storeout;

	//bi-directional many-to-one association to Storeoutrm
	@ManyToOne
	@JoinColumn(name="storeOutRMId")
	private Storeoutrm storeoutrm;

	public Storeoutrmassociation() {
	}
	public Storeoutrmassociation(int id) {
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

	public Storeout getStoreout() {
		return this.storeout;
	}

	public void setStoreout(Storeout storeout) {
		this.storeout = storeout;
	}

	public Storeoutrm getStoreoutrm() {
		return this.storeoutrm;
	}

	public void setStoreoutrm(Storeoutrm storeoutrm) {
		this.storeoutrm = storeoutrm;
	}

}