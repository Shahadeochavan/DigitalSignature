package com.nextech.erp.dto;

import com.nextech.erp.model.Product;

public class ProductInventoryDTO extends AbstractDTO{
	private String productPartNumber;
	private long inventoryQuantity;
	private long minimumQuantity;
	private long maximumQuantity;
	private String name;
	private long quantityAvailable;
	private long rackNumber;
	private Product productId;
	
	private long notificationId;
	public String getProductPartNumber() {
		return productPartNumber;
	}
	public void setProductPartNumber(String productPartNumber) {
		this.productPartNumber = productPartNumber;
	}
	public long getInventoryQuantity() {
		return inventoryQuantity;
	}
	public void setInventoryQuantity(long inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}
	public long getMinimumQuantity() {
		return minimumQuantity;
	}
	public void setMinimumQuantity(long minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}
	public long getMaximumQuantity() {
		return maximumQuantity;
	}
	public void setMaximumQuantity(long maximumQuantity) {
		this.maximumQuantity = maximumQuantity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getQuantityAvailable() {
		return quantityAvailable;
	}
	public void setQuantityAvailable(long quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}
	public long getRackNumber() {
		return rackNumber;
	}
	public void setRackNumber(long rackNumber) {
		this.rackNumber = rackNumber;
	}
	public Product getProductId() {
		return productId;
	}
	public void setProductId(Product productId) {
		this.productId = productId;
	}
	public long getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}


}
