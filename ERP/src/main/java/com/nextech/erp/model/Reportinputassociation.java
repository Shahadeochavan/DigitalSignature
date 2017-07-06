package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the reportinputassociation database table.
 * 
 */
@Entity
@NamedQuery(name="Reportinputassociation.findAll", query="SELECT r FROM Reportinputassociation r")
public class Reportinputassociation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	private boolean isactive;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to Report
	@ManyToOne
	@JoinColumn(name="reportId")
	private Report report;

	//bi-directional many-to-one association to Reportinputparameter
	@ManyToOne
	@JoinColumn(name="Report_input_parameter_id")
	private Reportinputparameter reportinputparameter;

	public Reportinputassociation() {
	}
	
	public Reportinputassociation(int id) {
		this.id=id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Report getReport() {
		return this.report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public Reportinputparameter getReportinputparameter() {
		return this.reportinputparameter;
	}

	public void setReportinputparameter(Reportinputparameter reportinputparameter) {
		this.reportinputparameter = reportinputparameter;
	}

}