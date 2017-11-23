package com.nextech.dscrm.newDTO;




import com.nextech.dscrm.dto.AbstractDTO;

public class NotificationUserAssociatinsDTO extends AbstractDTO {

	private boolean bcc;
	private boolean cc;
	private boolean to;
	private NotificationDTO notificationId;
	private UserDTO userId;
	
	public NotificationUserAssociatinsDTO(){
		
	}
	public NotificationUserAssociatinsDTO(int id){
		this.setId(id);
		
	}
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

	public NotificationDTO getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(NotificationDTO notificationId) {
		this.notificationId = notificationId;
	}
	public UserDTO getUserId() {
		return userId;
	}
	public void setUserId(UserDTO userId) {
		this.userId = userId;
	}
	public boolean getBcc() {
		return this.bcc;
	}

	public boolean getCc() {
		return this.cc;
	}

	public boolean getTo() {
		return this.to;
	}
	
	
}
