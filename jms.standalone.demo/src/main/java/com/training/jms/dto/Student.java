package com.training.jms.dto;

import java.io.Serializable;

public class Student implements Serializable {
	private static final long serialVersionUID = 8471889636166156674L;
	private int roll;
	private String firstName;
	private String lastName;
	private int standard;
	
	public int getRoll() {
		return roll;
	}
	public void setRoll(int roll) {
		this.roll = roll;
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
	public int getStandard() {
		return standard;
	}
	public void setStandard(int standard) {
		this.standard = standard;
	}
	
	@Override
	public String toString() {
		return "Student [roll=" + roll + ", firstName=" + firstName + ", lastName=" + lastName + ", standard="
				+ standard + "]";
	}
}
