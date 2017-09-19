package com.nextech.erp.dto;

import java.util.Date;
import java.util.List;

public class ReportQueryDataDTO {
	private long reportId;
	private long reportType;
	private Date searchByDate;
	private List<InputParameter> data;
	public long getReportId() {
		return reportId;
	}
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
	public long getReportType() {
		return reportType;
	}
	public void setReportType(long reportType) {
		this.reportType = reportType;
	}
	public List<InputParameter> getData() {
		return data;
	}
	public void setData(List<InputParameter> data) {
		this.data = data;
	}
	public Date getSearchByDate() {
		return searchByDate;
	}
	public void setSearchByDate(Date searchByDate) {
		this.searchByDate = searchByDate;
	}
}
