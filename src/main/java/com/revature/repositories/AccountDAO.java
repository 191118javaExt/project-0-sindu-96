package com.revature.repositories;

import java.util.List;

import com.revature.models.Account;

public interface AccountDAO {
	public Account getAccount(int a_id);
	public List<Account> findByU_id(int u_id);
	public boolean addAccount(int u_id);
	public List<Account> findUnapproved();
	public boolean approve(int a_id);
	public boolean withdraw(int a_id, double amount);
	public boolean deposit(int a_id, double amount);
}
