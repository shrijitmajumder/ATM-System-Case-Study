package com.capgemini.bank;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BankCustomer // Bank Customer class for instantiating customer details like name, address, email, ATM card and ATM branch
{
	
	String CustomerName;
	String Address;
	String Email;
	ATMCard Card;
	ATM atm;
	public BankCustomer(String customerName, String address, String email, ATMCard card, ATM atm) {
		CustomerName = customerName;
		Address = address;
		Email = email;
		Card = card;
		this.atm=atm;
	}
	
	public BankCustomer(ATMCard card) {
		Card=card;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		Date date=new Date();
		ATMCard card = Card;
		Account acc=card.Acc;
		if(acc.AccountType.toLowerCase().equals("savings"))
			return "Customer Name : "+CustomerName+"\nCustomer Address : "+Address+"\nCustomer Email : "+Email+"\nDate of Opening : "+sdf.format(date)+"\n"+Card+"\n"+atm;
		return "Company Name : "+CustomerName+"\nCompany Address : "+Address+"\nCompany Email : "+Email+"\nDate of Opening : "+sdf.format(date)+"\n"+Card+"\n"+atm;
	}
	
	
}
