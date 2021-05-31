package com.capgemini.bank;

public class ATMCard // ATMCard Class for instantiating ATM Card fields like PIN, card id and account
{
	
	int PIN;
	long CardID;
	Account Acc;
	public ATMCard(int pin, long cardID, Account acc) {
		super();
		PIN = pin;
		CardID = cardID;
		Acc = acc;
	}
	
	public ATMCard(Account acc) {
		Acc=acc;
	}
	
	@Override
	public String toString() {
		return Acc+"\nCard Number : "+CardID;
	}

	
}
