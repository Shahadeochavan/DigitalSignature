package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the usertype database table.
 * 
 */
@Entity
@NamedQuery(name="Usertype.findAll", query="SELECT u FROM Usertype u")
public class Usertype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@NotBlank(message="{description should not be blank}")
	@Size(min = 4, max = 255, message = "{description sholud be greater than 4 or less than 255 characters}")
	private String description;

	private boolean isactive;

	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	@NotBlank(message="{usertypeName should not be blank}")
	@Size(min = 2, max = 255, message = "{usertypeName sholud be greater than 2 or less than 255 characters}")
	@Column(name="usertype_name")
	private String usertypeName;

	//bi-directional many-to-one association to User
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usertype", cascade = CascadeType.ALL)
	private List<User> users;

	//bi-directional many-to-one association to Usertypepageassociation
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usertype", cascade = CascadeType.ALL)
	private List<Usertypepageassociation> usertypepageassociations;

	public Usertype() {
	}
	public Usertype(int id) {
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

	public String getUsertypeName() {
		return this.usertypeName;
	}

	public void setUsertypeName(String usertypeName) {
		this.usertypeName = usertypeName;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setUsertype(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setUsertype(null);

		return user;
	}

	public List<Usertypepageassociation> getUsertypepageassociations() {
		return this.usertypepageassociations;
	}

	public void setUsertypepageassociations(List<Usertypepageassociation> usertypepageassociations) {
		this.usertypepageassociations = usertypepageassociations;
	}

	public Usertypepageassociation addUsertypepageassociation(Usertypepageassociation usertypepageassociation) {
		getUsertypepageassociations().add(usertypepageassociation);
		usertypepageassociation.setUsertype(this);

		return usertypepageassociation;
	}

	public Usertypepageassociation removeUsertypepageassociation(Usertypepageassociation usertypepageassociation) {
		getUsertypepageassociations().remove(usertypepageassociation);
		usertypepageassociation.setUsertype(null);

		return usertypepageassociation;
	}

}