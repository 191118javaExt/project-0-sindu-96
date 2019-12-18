package com.revature.services;

import java.util.List;

import com.revature.MyLogger;
import com.revature.models.Account;
import com.revature.repositories.AccountDAOImpl;

public class AccountServices {
public static AccountDAOImpl ad = new AccountDAOImpl();
	
	public static Account getAccount(int a_id) {
		return ad.getAccount(a_id);	
	}
	
	public static List<Account> getAccounts(int u_id) {
		return ad.findByU_id(u_id);
	}
	
	public static boolean addAccount(int u_id) {
		return ad.addAccount(u_id);
	}
	
	public static List<Account> getUnapproved() {
		return ad.findUnapproved();
	}
	
	public static boolean approve(int a_id) {
		return ad.approve(a_id);
	}
	
	public static boolean withdraw(Account a, double amount) {
		if (a.getBalance() - amount < 0) {
			MyLogger.logger.warn("user tried to withdraw negative balance");
			return false;
		} else {
			return ad.withdraw(a.getA_id(), amount);
		}
	}
	
	public static boolean deposit(int a_id, double amount) {
		return ad.deposit(a_id, amount);
	}
	
	public static boolean transfer(Account a, int a_id2,  double amount) {
		if (a.getBalance() - amount < 0) {
			MyLogger.logger.warn("user tried to transfer negative balance");
			return false;
		} else {
			return ad.transfer(a.getA_id(), a_id2, amount);
		}
	}
}
