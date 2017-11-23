package com.nextech.dscrm.newDTO;



import com.nextech.dscrm.dto.AbstractDTO;
import com.nextech.dscrm.model.Status;

public class ProductInventoryhistoryDTO extends AbstractDTO{

	private long afterQuantity;
	private long beforeQuantity;
	private Status statusId;
	public long getAfterQuantity() {
		return afterQuantity;
	}
	public void setAfterQuantity(long afterQuantity) {
		this.afterQuantity = afterQuantity;
	}
	public long getBeforeQuantity() {
		return beforeQuantity;
	}
	public void setBeforeQuantity(long beforeQuantity) {
		this.beforeQuantity = beforeQuantity;
	}
	public Status getStatusId() {
		return statusId;
	}
	public void setStatusId(Status statusId) {
		this.statusId = statusId;
	}
	
}
