package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the notification database table.
 *
 */
@Entity
@NamedQuery(name="Notification.findAll", query="SELECT n FROM Notification n")
public class Notification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String beanClass;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	private String description;

	private boolean isactive;

	private String name;

	private String subject;

	private String template;

	private String type;

	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to Status
	@ManyToOne
	@JoinColumn(name="fromStatus")
	private Status status1;

	//bi-directional many-to-one association to Status
	@ManyToOne
	@JoinColumn(name="toStatus")
	private Status status2;

	//bi-directional many-to-one association to Notificationuserassociation
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "notification", cascade = CascadeType.ALL)
	private List<Notificationuserassociation> notificationuserassociations;

	public Notification() {
	}

	public Notification(int id) {
		this.id=id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBeanClass() {
		return this.beanClass;
	}

	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTemplate() {
		return this.template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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

	public List<Notificationuserassociation> getNotificationuserassociations() {
		return this.notificationuserassociations;
	}

	public void setNotificationuserassociations(List<Notificationuserassociation> notificationuserassociations) {
		this.notificationuserassociations = notificationuserassociations;
	}

	public Notificationuserassociation addNotificationuserassociation(Notificationuserassociation notificationuserassociation) {
		getNotificationuserassociations().add(notificationuserassociation);
		notificationuserassociation.setNotification(this);

		return notificationuserassociation;
	}

	public Notificationuserassociation removeNotificationuserassociation(Notificationuserassociation notificationuserassociation) {
		getNotificationuserassociations().remove(notificationuserassociation);
		notificationuserassociation.setNotification(null);

		return notificationuserassociation;
	}

}