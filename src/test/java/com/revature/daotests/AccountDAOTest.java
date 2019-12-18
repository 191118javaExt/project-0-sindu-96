package com.revature.daotests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Test;

import com.revature.models.Account;
import com.revature.services.AccountServices;

public class AccountDAOTest {
	@Test
	public void addAccount() {
		int num = AccountServices.getAccounts(2).size();
		AccountServices.addAccount(2);
		assertTrue(num + 1 == AccountServices.getAccounts(2).size());
	}
	
	@Test
	public void withdrawNegative() {
		Account a = AccountServices.getAccount(4);
		double amount = a.getBalance() + 10;  
		assertFalse(AccountServices.withdraw(a, amount));
	}

	@Test
	public void withdrawValidAmount( ) {
		Account a = AccountServices.getAccount(4);
		double amount = a.getBalance() - 10; 
		AccountServices.withdraw(a, amount);
		Account updated = AccountServices.getAccount(4);
		assertTrue(10.00 == updated.getBalance());
	}
	
	@AfterClass
	public static void deposit( ) {
		Account a = AccountServices.getAccount(4);
		AccountServices.deposit(a.getA_id(), 200);
		Account updated = AccountServices.getAccount(4);
		assertTrue(updated.getBalance() == a.getBalance() + 200);
	}
}
