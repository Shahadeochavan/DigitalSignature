package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the reportoutputassociation database table.
 * 
 */
@Entity
@NamedQuery(name="Reportoutputassociation.findAll", query="SELECT r FROM Reportoutputassociation r")
public class Reportoutputassociation implements Serializable {
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

	//bi-directional many-to-one association to Reportoutputparameter
	@ManyToOne
	@JoinColumn(name="Report_output_parameter_id")
	private Reportoutputparameter reportoutputparameter;

	public Reportoutputassociation() {
	}
	public Reportoutputassociation(int id) {
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

	public Reportoutputparameter getReportoutputparameter() {
		return this.reportoutputparameter;
	}

	public void setReportoutputparameter(Reportoutputparameter reportoutputparameter) {
		this.reportoutputparameter = reportoutputparameter;
	}

}