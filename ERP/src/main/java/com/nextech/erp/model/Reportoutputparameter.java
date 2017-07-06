package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the reportoutputparameter database table.
 * 
 */
@Entity
@NamedQuery(name="Reportoutputparameter.findAll", query="SELECT r FROM Reportoutputparameter r")
public class Reportoutputparameter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;

	@Column(name="created_by")
	private int createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="display_name")
	private String displayName;

	private boolean isactive;

	private String name;

	private String datatype;
	
	@Column(name="updated_by")
	private int updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to Reportoutputassociation
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "reportoutputparameter", cascade = CascadeType.ALL)
	private List<Reportoutputassociation> reportoutputassociations;

	public Reportoutputparameter() {
	}
	public Reportoutputparameter(int id) {
		this.id=id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	public List<Reportoutputassociation> getReportoutputassociations() {
		return this.reportoutputassociations;
	}

	public void setReportoutputassociations(List<Reportoutputassociation> reportoutputassociations) {
		this.reportoutputassociations = reportoutputassociations;
	}

	public Reportoutputassociation addReportoutputassociation(Reportoutputassociation reportoutputassociation) {
		getReportoutputassociations().add(reportoutputassociation);
		reportoutputassociation.setReportoutputparameter(this);

		return reportoutputassociation;
	}

	public Reportoutputassociation removeReportoutputassociation(Reportoutputassociation reportoutputassociation) {
		getReportoutputassociations().remove(reportoutputassociation);
		reportoutputassociation.setReportoutputparameter(null);

		return reportoutputassociation;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

}