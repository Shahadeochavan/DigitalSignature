package com.nextech.erp.newDTO;
import java.util.Date;
import java.util.List;
import com.nextech.erp.dto.AbstractDTO;

public class UserDTO extends AbstractDTO {
	private String userId;
	private String password;
	private String firstName;
	private String lastName;
	private String mobileNo;
	private UserTypeDTO userTypeDTO;
	private Date dob;
	private Date doj;
	private String emailId;
	private List<NotificationUserAssociatinsDTO> notificationUserAssociatinsDTOs;
	public UserDTO(int id){
		this.setId(id);
	}
	public UserDTO() {
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Date getDoj() {
		return doj;
	}
	public void setDoj(Date doj) {
		this.doj = doj;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public List<NotificationUserAssociatinsDTO> getNotificationUserAssociatinsDTOs() {
		return notificationUserAssociatinsDTOs;
	}
	public void setNotificationUserAssociatinsDTOs(
			List<NotificationUserAssociatinsDTO> notificationUserAssociatinsDTOs) {
		this.notificationUserAssociatinsDTOs = notificationUserAssociatinsDTOs;
	}
	public UserTypeDTO getUserTypeDTO() {
		return userTypeDTO;
	}
	public void setUserTypeDTO(UserTypeDTO userTypeDTO) {
		this.userTypeDTO = userTypeDTO;
	}
	
}
