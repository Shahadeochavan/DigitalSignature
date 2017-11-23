package com.nextech.dscrm.dto;



public class BankdetailDTO extends AbstractDTO{
	
	private String bankName;
	private String accountNumber;
	private String branchName;
	private String ifscCoe;
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getIfscCoe() {
		return ifscCoe;
	}
	public void setIfscCoe(String ifscCoe) {
		this.ifscCoe = ifscCoe;
	}

}
