package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the rawmaterialorderinvoiceassociation database table.
 * 
 */
@Entity
@NamedQuery(name="Rawmaterialorderinvoiceassociation.findAll", query="SELECT r FROM Rawmaterialorderinvoiceassociation r")
public class Rawmaterialorderinvoiceassociation implements Serializable {
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

	//bi-directional many-to-one association to Rawmaterialorder
	@ManyToOne
	@JoinColumn(name="rawmaterialorderid")
	private Rawmaterialorder rawmaterialorder;

	//bi-directional many-to-one association to Rawmaterialorderinvoice
	@ManyToOne
	@JoinColumn(name="rmorderinvoiceid")
	private Rawmaterialorderinvoice rawmaterialorderinvoice;

	public Rawmaterialorderinvoiceassociation() {
	}
	public Rawmaterialorderinvoiceassociation(int id) {
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

	public Rawmaterialorder getRawmaterialorder() {
		return this.rawmaterialorder;
	}

	public void setRawmaterialorder(Rawmaterialorder rawmaterialorder) {
		this.rawmaterialorder = rawmaterialorder;
	}

	public Rawmaterialorderinvoice getRawmaterialorderinvoice() {
		return this.rawmaterialorderinvoice;
	}

	public void setRawmaterialorderinvoice(Rawmaterialorderinvoice rawmaterialorderinvoice) {
		this.rawmaterialorderinvoice = rawmaterialorderinvoice;
	}

}