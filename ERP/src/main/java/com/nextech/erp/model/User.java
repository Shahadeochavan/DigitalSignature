package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;
import java.sql.Timestamp;


/**
 * The persistent class for the user database table.
 *
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Temporal(TemporalType.DATE)
	private Date dob;

	@Temporal(TemporalType.DATE)
	private Date doj;

	@Email(message="{email should be enter valid")
	@Size(min = 2, max = 255, message = "{Email sholud be greater than 2 or less than 255 characters}")
	private String email;

	@NotBlank(message="{first Name should not be blank}")
	@Size(min = 2, max = 255, message = "{first Name sholud be greater than 2 or less than 255 characters}")
	@Column(name="first_name")
	private String firstName;

	private boolean isactive;

	@NotBlank(message="{last Name should not be blank}")
	@Size(min = 2, max = 255, message = "{last Name sholud be greater than 2 or less than 255 characters}")
	@Column(name="last_name")
	private String lastName;

	@Size(min = 10, max = 10, message = "{contact number should be 10 digits}")
	private String mobile;

	@NotBlank(message="{password should not be blank}")
	@Size(min = 5, max = 255, message = "{password sholud be greater than 5 or less than 255 characters or digits}")
	private String password;

	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	@NotBlank(message="{userid should not be blank}")
	@Size(min = 5, max = 255, message = "{userid sholud be greater than 5 or less than 255 characters or digits}")
	private String userid;

	//bi-directional many-to-one association to Usertype
	@ManyToOne
	@JoinColumn(name="usertypeid")
	private Usertype usertype;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private List<Notificationuserassociation> notificationuserassociations;

	public User() {
	}
	public User(int id) {
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

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getDoj() {
		return this.doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Usertype getUsertype() {
		return this.usertype;
	}

	public void setUsertype(Usertype usertype) {
		this.usertype = usertype;
	}
	public List<Notificationuserassociation> getNotificationuserassociations() {
		return this.notificationuserassociations;
	}

	public void setNotificationuserassociations(List<Notificationuserassociation> notificationuserassociations) {
		this.notificationuserassociations = notificationuserassociations;
	}

	public Notificationuserassociation addNotificationuserassociation(Notificationuserassociation notificationuserassociation) {
		getNotificationuserassociations().add(notificationuserassociation);
		notificationuserassociation.setUser(this);

		return notificationuserassociation;
	}

	public Notificationuserassociation removeNotificationuserassociation(Notificationuserassociation notificationuserassociation) {
		getNotificationuserassociations().remove(notificationuserassociation);
		notificationuserassociation.setUser(null);

		return notificationuserassociation;
	}
}