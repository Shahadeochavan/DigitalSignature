package com.nextech.erp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the statustransition database table.
 * 
 */
@Entity
@NamedQuery(name="Statustransition.findAll", query="SELECT s FROM Statustransition s")
public class Statustransition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	private String description;

	@Column(name="from_status")
	private String fromStatus;

	private boolean isactive;

	private String isNotificationEmail;

	private String isNotificationSMS;

	private String name;

	@Column(name="to_status")
	private String toStatus;

	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	public Statustransition() {
	}
	public Statustransition(int id) {
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

	public String getFromStatus() {
		return this.fromStatus;
	}

	public void setFromStatus(String fromStatus) {
		this.fromStatus = fromStatus;
	}

	public boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public String getIsNotificationEmail() {
		return this.isNotificationEmail;
	}

	public void setIsNotificationEmail(String isNotificationEmail) {
		this.isNotificationEmail = isNotificationEmail;
	}

	public String getIsNotificationSMS() {
		return this.isNotificationSMS;
	}

	public void setIsNotificationSMS(String isNotificationSMS) {
		this.isNotificationSMS = isNotificationSMS;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToStatus() {
		return this.toStatus;
	}

	public void setToStatus(String toStatus) {
		this.toStatus = toStatus;
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

}