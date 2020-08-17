package com.da.model;

import java.util.List;

public class Patient {

	private String fName;
	private String lName;
	private String mobile;
	private Integer age;
	private boolean deleted;
	private List<Address> addresses;
	
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	
	@Override
	public String toString() {
		return "Patient [fName=" + fName + ", lName=" + lName + ", mobile=" + mobile + ", age=" + age + ", deleted="
				+ deleted + ", addresses=" + addresses + "]";
	}
}
