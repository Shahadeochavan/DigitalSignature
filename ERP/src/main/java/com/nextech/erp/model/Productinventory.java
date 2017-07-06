package com.nextech.erp.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the productinventory database table.
 *
 */
@Entity
@NamedQuery(name="Productinventory.findAll", query="SELECT p FROM Productinventory p")
public class Productinventory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="created_by")
	private long createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Size(min = 4, max = 255, message = "{description sholud be greater than 4 or less than 255 characters}")
	private String description;

	private long minimum_quantity;
	private long maximum_quantity;
	private boolean isactive;

	/*@NotBlank(message="{name should not be blank}")
	@Size(min = 2, max = 255, message = "{name sholud be greater than 2 or less than 255 characters}")*/
	private String name;


   // @Min(value = 0, message = " please enter quantityavailabl")
	private long quantityavailable;

/*    @Min(value = 1, message = "racknumber should be greater than 1 digit")
	 @Max(value = 100, message = "racknumber should be less than 100 digit")*/
	private long racknumber;

	@Column(name="updated_by")
	private long updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="productid")
	private Product product;

	//bi-directional many-to-one association to Productinventoryhistory
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productinventory", cascade = CascadeType.ALL)
	private List<Productinventoryhistory> productinventoryhistories;

	public Productinventory() {
	}
	public Productinventory(int id) {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getQuantityavailable() {
		return this.quantityavailable;
	}

	public void setQuantityavailable(long quantityavailable) {
		this.quantityavailable = quantityavailable;
	}

	public long getRacknumber() {
		return this.racknumber;
	}

	public void setRacknumber(long racknumber) {
		this.racknumber = racknumber;
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

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getMinimum_quantity() {
		return minimum_quantity;
	}
	public void setMinimum_quantity(long minimum_quantity) {
		this.minimum_quantity = minimum_quantity;
	}
	public long getMaximum_quantity() {
		return maximum_quantity;
	}
	public void setMaximum_quantity(long maximum_quantity) {
		this.maximum_quantity = maximum_quantity;
	}
	public List<Productinventoryhistory> getProductinventoryhistories() {
		return this.productinventoryhistories;
	}

	public void setProductinventoryhistories(List<Productinventoryhistory> productinventoryhistories) {
		this.productinventoryhistories = productinventoryhistories;
	}

	public Productinventoryhistory addProductinventoryhistory(Productinventoryhistory productinventoryhistory) {
		getProductinventoryhistories().add(productinventoryhistory);
		productinventoryhistory.setProductinventory(this);

		return productinventoryhistory;
	}

	public Productinventoryhistory removeProductinventoryhistory(Productinventoryhistory productinventoryhistory) {
		getProductinventoryhistories().remove(productinventoryhistory);
		productinventoryhistory.setProductinventory(null);

		return productinventoryhistory;
	}

}