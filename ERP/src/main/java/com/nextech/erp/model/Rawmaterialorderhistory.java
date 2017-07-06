package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;


/**
 * The persistent class for the rawmaterialorderhistory database table.
 * 
 */
@Entity
@NamedQuery(name="Rawmaterialorderhistory.findAll", query="SELECT r FROM Rawmaterialorderhistory r")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rawmaterialorderhistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;

	private String comment;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	private String description;

	private boolean isactive;

	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to Rawmaterialorder
	@ManyToOne
	private Rawmaterialorder rawmaterialorder;

	//bi-directional many-to-one association to Status
	@ManyToOne
	@JoinColumn(name="status_id_from")
	private Status status1;

	//bi-directional many-to-one association to Status
	@ManyToOne
	@JoinColumn(name="status_id_to")
	private Status status2;

	//bi-directional many-to-one association to Rawmaterialorderinvoice
	@ManyToOne
	@JoinColumn(name="rmorderinvoiceid")
	private Rawmaterialorderinvoice rawmaterialorderinvoice;

	//bi-directional many-to-one association to Qualitycheckrawmaterial
	@ManyToOne
	@JoinColumn(name="rmqualitycheckid")
	private Qualitycheckrawmaterial qualitycheckrawmaterial;

	public Rawmaterialorderhistory() {
	}
	public Rawmaterialorderhistory(int id) {
		this.id=id;
	}
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public Status getStatus1() {
		return this.status1;
	}

	public void setStatus1(Status status1) {
		this.status1 = status1;
	}

	public Status getStatus2() {
		return this.status2;
	}

	public void setStatus2(Status status2) {
		this.status2 = status2;
	}

	  @JsonIgnore
	    @JsonProperty(value = "rmorderinvoiceid")
	public Rawmaterialorderinvoice getRawmaterialorderinvoice() {
		return this.rawmaterialorderinvoice;
	}

	public void setRawmaterialorderinvoice(Rawmaterialorderinvoice rawmaterialorderinvoice) {
		this.rawmaterialorderinvoice = rawmaterialorderinvoice;
	}

	  @JsonIgnore
	    @JsonProperty(value = "rmqualitycheckid")
	public Qualitycheckrawmaterial getQualitycheckrawmaterial() {
		return this.qualitycheckrawmaterial;
	}

	public void setQualitycheckrawmaterial(Qualitycheckrawmaterial qualitycheckrawmaterial) {
		this.qualitycheckrawmaterial = qualitycheckrawmaterial;
	}

}