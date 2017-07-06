package com.nextech.erp.model;

import java.io.Serializable;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the client database table.
 * 
 */
@Entity
@NamedQuery(name="Client.findAll", query="SELECT c FROM Client c")
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;

	private String address;

	private String commisionerate;

	private String companyname;

	private String contactnumber;

	private String contactpersonname;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	private String cst;

	@Column(name="customer_ecc_number")
	private String customerEccNumber;

	private String description;

	private String division;

	private String emailid;

	private boolean isactive;
	private String  renge;

	//private String range;
//add code
	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	@Column(name="vat_no")
	private String vatNo;

	//bi-directional many-to-one association to Productorder

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
	private List<Productorder> productorders;

	public Client() {
	}
	public Client(int id ) {
		this.id=id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCommisionerate() {
		return this.commisionerate;
	}

	public void setCommisionerate(String commisionerate) {
		this.commisionerate = commisionerate;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getContactnumber() {
		return this.contactnumber;
	}

	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}

	public String getContactpersonname() {
		return this.contactpersonname;
	}

	public void setContactpersonname(String contactpersonname) {
		this.contactpersonname = contactpersonname;
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

	public String getCst() {
		return this.cst;
	}

	public void setCst(String cst) {
		this.cst = cst;
	}

	public String getCustomerEccNumber() {
		return this.customerEccNumber;
	}

	public void setCustomerEccNumber(String customerEccNumber) {
		this.customerEccNumber = customerEccNumber;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDivision() {
		return this.division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getEmailid() {
		return this.emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}
	
	

	/*public String getRange() {
		return this.range;
	}

	public void setRange(String range) {
		this.range = range;
	}*/

	public String getRenge() {
		return renge;
	}
	public void setRenge(String renge) {
		this.renge = renge;
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

	public String getVatNo() {
		return this.vatNo;
	}

	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}

	public List<Productorder> getProductorders() {
		return this.productorders;
	}

	public void setProductorders(List<Productorder> productorders) {
		this.productorders = productorders;
	}

	public Productorder addProductorder(Productorder productorder) {
		getProductorders().add(productorder);
		productorder.setClient(this);

		return productorder;
	}

	public Productorder removeProductorder(Productorder productorder) {
		getProductorders().remove(productorder);
		productorder.setClient(null);

		return productorder;
	}

}