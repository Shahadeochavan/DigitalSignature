package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the report database table.
 * 
 */
@Entity
@NamedQuery(name="Report.findAll", query="SELECT r FROM Report r")
public class Report implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;

	@Column(name="created_by")
	private int createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="file_name")
	private String fileName;

	private boolean isactive;

	@Column(name="report_location")
	private String reportLocation;

	private String report_Name;

	@Column(name="report_query")
	private String reportQuery;

	@Column(name="updated_by")
	private int updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to Reportinputassociation
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "report", cascade = CascadeType.ALL)
	private List<Reportinputassociation> reportinputassociations;

	//bi-directional many-to-one association to Reportoutputassociation
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "report", cascade = CascadeType.ALL)
	private List<Reportoutputassociation> reportoutputassociations;

	//bi-directional many-to-one association to Reportusertypeassociation
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "report", cascade = CascadeType.ALL)
	private List<Reportusertypeassociation> reportusertypeassociations;

	public Report() {
	}
	
	public Report(int id) {
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

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public String getReportLocation() {
		return this.reportLocation;
	}

	public void setReportLocation(String reportLocation) {
		this.reportLocation = reportLocation;
	}

	public String getReport_Name() {
		return this.report_Name;
	}

	public void setReport_Name(String report_Name) {
		this.report_Name = report_Name;
	}

	public String getReportQuery() {
		return this.reportQuery;
	}

	public void setReportQuery(String reportQuery) {
		this.reportQuery = reportQuery;
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

	public List<Reportinputassociation> getReportinputassociations() {
		return this.reportinputassociations;
	}

	public void setReportinputassociations(List<Reportinputassociation> reportinputassociations) {
		this.reportinputassociations = reportinputassociations;
	}

	public Reportinputassociation addReportinputassociation(Reportinputassociation reportinputassociation) {
		getReportinputassociations().add(reportinputassociation);
		reportinputassociation.setReport(this);

		return reportinputassociation;
	}

	public Reportinputassociation removeReportinputassociation(Reportinputassociation reportinputassociation) {
		getReportinputassociations().remove(reportinputassociation);
		reportinputassociation.setReport(null);

		return reportinputassociation;
	}

	public List<Reportoutputassociation> getReportoutputassociations() {
		return this.reportoutputassociations;
	}

	public void setReportoutputassociations(List<Reportoutputassociation> reportoutputassociations) {
		this.reportoutputassociations = reportoutputassociations;
	}

	public Reportoutputassociation addReportoutputassociation(Reportoutputassociation reportoutputassociation) {
		getReportoutputassociations().add(reportoutputassociation);
		reportoutputassociation.setReport(this);

		return reportoutputassociation;
	}

	public Reportoutputassociation removeReportoutputassociation(Reportoutputassociation reportoutputassociation) {
		getReportoutputassociations().remove(reportoutputassociation);
		reportoutputassociation.setReport(null);

		return reportoutputassociation;
	}

	public List<Reportusertypeassociation> getReportusertypeassociations() {
		return this.reportusertypeassociations;
	}

	public void setReportusertypeassociations(List<Reportusertypeassociation> reportusertypeassociations) {
		this.reportusertypeassociations = reportusertypeassociations;
	}

	public Reportusertypeassociation addReportusertypeassociation(Reportusertypeassociation reportusertypeassociation) {
		getReportusertypeassociations().add(reportusertypeassociation);
		reportusertypeassociation.setReport(this);

		return reportusertypeassociation;
	}

	public Reportusertypeassociation removeReportusertypeassociation(Reportusertypeassociation reportusertypeassociation) {
		getReportusertypeassociations().remove(reportusertypeassociation);
		reportusertypeassociation.setReport(null);

		return reportusertypeassociation;
	}

}