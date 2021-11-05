package com.bank.jdbc;

public class Account {
	private int accountNumber;
	private String name;
	private double balance;
	private double fee;
	
	
	public Account(int accountNumber, String name, double balance, double fee) {
		super();
		this.accountNumber = accountNumber;
		this.name = name;
		this.balance = balance;
		this.fee = fee;
	}
	public Account() {
		
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public String getName() {
		return name;
	}
	public double getBalance() {
		return balance;
	}
	public double getFee() {
		return fee;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	
}