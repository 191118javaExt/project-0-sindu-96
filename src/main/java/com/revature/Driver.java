package com.revature;

import java.util.List;
import java.util.Scanner;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.AccountServices;
import com.revature.services.UserServices;

public class Driver {
	static Scanner scan = new Scanner(System.in);
	static User currentUser;

	public static void main(String[] args) {
		mainMenu();
	}
	
	public static void mainMenu() {
		System.out.println("1. Register\n2. Login");

		int i = scan.nextInt();
		scan.nextLine();
		
		switch (i) {
			case 1: {
				register();
				break;
			}
			case 2: {
				login();
				break;
			}
			default: {
				System.out.println("Invalid input");
				mainMenu();
			}
		}
		
	}
	
	public static void register() {
		System.out.println("Enter your first name");
		String firstName = scan.nextLine();
		System.out.println("Enter your last name");
		String lastName = scan.nextLine();
		System.out.println("Enter your username");
		String username = scan.nextLine();
		System.out.println("Enter your password");
		int password = scan.nextLine().hashCode();
		System.out.println(firstName + " " + username + " " + password);
		
		currentUser = new User(firstName, lastName, username, password);
	
		if (UserServices.addUser(currentUser)) {
			MyLogger.logger.info("user successfully registered");
			
			mainMenu();
		}
		
	}
	
	public static void login() {
		System.out.println("Enter your username");
		String username = scan.nextLine();
		System.out.println("Enter your password");
		int password = scan.nextLine().hashCode();
		
		currentUser = UserServices.getUser(username, password);
		System.out.println(currentUser.toString());
		
		if (currentUser != null) {
			MyLogger.logger.info("User successfully logged in");
			userMenu();
		}
	}

	public static void userMenu() {
		System.out.println("1. Create new account\n2. Choose account");
		
		int i = scan.nextInt();
		scan.nextLine();
		
		switch (i) {
			case 1: {
				if (AccountServices.addAccount(currentUser.getU_id())) {
					System.out.println("Account created");
					MyLogger.logger.info("account created for user " + currentUser.getUsername());
					userMenu();
				} else {
					System.out.println("error?");
				}
				break;
			}
			case 2: {
				List<Account> allAccounts = AccountServices.getAccounts(currentUser.getU_id());

				for(int j = 0; j < allAccounts.size(); j++) {
					System.out.println(j+1 +". "+allAccounts.get(j).getA_id());
				}
				
				int selectedInx = scan.nextInt();
				scan.nextLine();

				if(selectedInx < 1 || selectedInx > allAccounts.size()) {
					System.out.println("Invalid input");
					userMenu();
				} else {
					Account chosenAccount = allAccounts.get(selectedInx - 1);
					
					accountMenu(chosenAccount);
				}
				
				break;
			}
			default: {
				System.out.println("Invalid input");
				userMenu();
			}
		}
		
	}
	
	public static void accountMenu(Account a) {
		System.out.println("1. View balance\n2. Withdraw\n3. Deposit\n4. Transfer\n5. Back to User Menu");
		
		int i = scan.nextInt();
		scan.nextLine();
		
		switch (i) {
			case 1: {
				System.out.println(a.toString());
				accountMenu(a);
				break;
			}
			case 2: {
				System.out.println("Enter amount to withdraw");
				double amount = scan.nextDouble();
				scan.nextLine();
				
				if (AccountServices.withdraw(a, amount)) {
					System.out.println("Balance updated");
					MyLogger.logger.info("Balance updated");
					a = AccountServices.getAccount(a.getA_id());
					System.out.println(a.toString());
					accountMenu(a);
				} else {
					System.out.println("Balance too low");
					accountMenu(a);
				}
				
				break;
			}
			case 3: {
				System.out.println("Enter amount to deposit");
				double amount = scan.nextDouble();
				scan.nextLine();
				
			    AccountServices.deposit(a.getA_id(), amount);
				System.out.println("Balance updated");
				MyLogger.logger.info("Balance updated");
				a = AccountServices.getAccount(a.getA_id());
				System.out.println(a.toString());
				accountMenu(a);
				
				break;
			}
			case 4: {
	
				break;
			}
			case 5: {
				userMenu();
				break;
			}
			default: {
				System.out.println("Invalid input");
				accountMenu(a);
			}
		}
	}
}
