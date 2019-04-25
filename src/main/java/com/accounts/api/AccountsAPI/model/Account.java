package com.accounts.api.AccountsAPI.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Document
@ApiModel(description="All Account Specific information.")
public class Account {

	@Id
	private String id;
	
	@Min(value=10)
	@ApiModelProperty(notes= "Account Opening Balance should be more than 10 GBP")
	private Double accountBalance;
	private String accountType;
	
	@Size(min=3)
	@ApiModelProperty(notes= "Customer Account Name Should have at least 3 Characters.")
	private String customerName;
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public Account() {
	}
	
	public Account(String id, Double accountBalance, String accountType) {
		super();
		this.id = id;
		this.accountBalance = accountBalance;
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", accountBalance=" + accountBalance + ", accountType=" + accountType
				+ ", customerName=" + customerName ;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	
	
}
