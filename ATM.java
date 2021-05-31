package com.capgemini.bank;

public class ATM // ATM class for instantiating ATM branch fields like location and branch name
{
	
	String Location;
	String BranchName;
	
	public ATM() {
		
	}
	public ATM(String location, String branchName) {
		super();
		Location = location;
		BranchName = branchName;
	}

	@Override
	public String toString() {
		return "Branch Name : " + BranchName+"\nLocation : " + Location;
	}
	
	

}
