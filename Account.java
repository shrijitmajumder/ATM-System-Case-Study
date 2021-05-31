package com.capgemini.bank;

public class Account // Account Class for instantiating Account fields like account no. , balance and account type
{
	
	int IAccountNumber;
	double Balance;
	String AccountType;
	
	public Account(int iAccountNumber, double balance, String accountType) {
		super();
		IAccountNumber = iAccountNumber;
		Balance = balance;
		AccountType = accountType;
	}

	@Override
	public String toString() {
		String sm_decimal_format="%.2f";
		return "Account Number : " + IAccountNumber + "\nBalance : Rs." + sm_decimal_format.formatted(Balance) + "\nAccount Type : " + AccountType;
	}
	
	
}
