package com.revature.models;

public class Account {
	private int A_id;
	private int U_id;
	private double balance;
	private int status;
	
	public Account(int a_id, int u_id, double balance, int status) {
		super();
		A_id = a_id;
		U_id = u_id;
		this.balance = balance;
		this.status = status;
	}

	public Account(int u_id, double balance, int status) {
		super();
		U_id = u_id;
		this.balance = balance;
		this.status = status;
	}

	public int getA_id() {
		return A_id;
	}

	public void setA_id(int a_id) {
		A_id = a_id;
	}

	public int getU_id() {
		return U_id;
	}

	public void setU_id(int u_id) {
		U_id = u_id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Account Number: " + A_id + "\tBalance: $" + balance;
	}

}
