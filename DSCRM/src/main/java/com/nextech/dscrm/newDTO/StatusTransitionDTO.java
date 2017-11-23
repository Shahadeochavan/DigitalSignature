package com.nextech.dscrm.newDTO;



import com.nextech.dscrm.dto.AbstractDTO;

public class StatusTransitionDTO extends AbstractDTO{
	
	private String fromStatus;
	private String isNotificationEmail;
	private String isNotificationSMS;
	private String name;
	private String toStatus;
	public String getFromStatus() {
		return fromStatus;
	}
	public void setFromStatus(String fromStatus) {
		this.fromStatus = fromStatus;
	}
	public String getIsNotificationEmail() {
		return isNotificationEmail;
	}
	public void setIsNotificationEmail(String isNotificationEmail) {
		this.isNotificationEmail = isNotificationEmail;
	}
	public String getIsNotificationSMS() {
		return isNotificationSMS;
	}
	public void setIsNotificationSMS(String isNotificationSMS) {
		this.isNotificationSMS = isNotificationSMS;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getToStatus() {
		return toStatus;
	}
	public void setToStatus(String toStatus) {
		this.toStatus = toStatus;
	}
}
