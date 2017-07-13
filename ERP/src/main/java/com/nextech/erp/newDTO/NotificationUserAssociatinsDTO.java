package com.nextech.erp.newDTO;



import com.nextech.erp.dto.AbstractDTO;
import com.nextech.erp.model.Notification;
import com.nextech.erp.model.User;

public class NotificationUserAssociatinsDTO extends AbstractDTO {

	private boolean bcc;
	private boolean cc;
	private boolean to;
	private Notification notificationId;
	private User userId;
	public boolean isBcc() {
		return bcc;
	}
	public void setBcc(boolean bcc) {
		this.bcc = bcc;
	}
	public boolean isCc() {
		return cc;
	}
	public void setCc(boolean cc) {
		this.cc = cc;
	}
	public boolean isTo() {
		return to;
	}
	public void setTo(boolean to) {
		this.to = to;
	}
	public Notification getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(Notification notificationId) {
		this.notificationId = notificationId;
	}
	public User getUserId() {
		return userId;
	}
	public void setUserId(User userId) {
		this.userId = userId;
	}
	
	
}
