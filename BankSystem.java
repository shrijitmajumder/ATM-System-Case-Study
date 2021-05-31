//	Program to show all functionalities of a Banking System.

package com.capgemini.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class BankSystem {

	static String location[]= {"Shyambazar","College Street","Salt Lake","Middleton Street","Mahendra Srimani"};
	static String BranchName[]= {"SBI Bhupen Bose Avenue","SBI Calcutta University","SBI Kolkata Main","SBI Service Branch","SBI Amherst Street"};
	static BankCustomer bc;
	static ATMCard Card;
	static Account acc;
	static ArrayList<BankCustomer> AccountList=new ArrayList<BankCustomer>();
	static double AvailableCash = (double)(Math.random()*Math.pow(10, 5));

	// function to create account with data validation for each field
	public static void CreateAccount() throws IOException {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));// BufferedReader object creation by Shrijit Majumder
		String CustomerName;
		String Address;
		String Email;
		int accountNum;
		int choice;
		System.out.println();
		for(int i=0;i<location.length;i++) {
			System.out.println((i+1)+". "+BranchName[i]+", "+location[i]);
		}
		do {
			System.out.print("\nAt which branch do you want to want to open the account : ");
			choice=Integer.parseInt(br.readLine());
			if(choice<=0 || choice>5) {
				System.out.println("\nChoice is invalid...Try Again...");
			}
		}while(choice>5 || choice<=0);

		String AccountType;
		int factor=0;
		do {
			System.out.print("\nEnter the type of Account you want to create(Savings/Current) : ");
			AccountType=br.readLine();
			if(AccountType.toLowerCase().equals("savings") || AccountType.toLowerCase().equals("current")) {
				break;
			}else {
				System.out.println("\nAccount Type can be either \"Savings\" or \"Current\".");
			}
		}while(factor==0);
		boolean nameresult=false;
		do {
			if(AccountType.toLowerCase().equals("savings")) {
				System.out.print("\nEnter Customer Name : ");
				CustomerName=br.readLine();
			}else {
				System.out.print("\nEnter Company Name : ");
				CustomerName=br.readLine();
			}
			nameresult=Pattern.matches("^[A-Za-z]+[A-Za-z ]+$", CustomerName);
			if(nameresult==false) {
				System.out.println("\nName must only consists of alphabets and must be of minimum length 2 and must not contain space at the beginning...Try Again...");
			}
		}while(nameresult==false);
		System.out.print("\nEnter Address : ");
		Address=br.readLine();
		boolean emailresult=false;
		do {
			System.out.print("\nEnter Email : ");
			Email=br.readLine();
			emailresult = Pattern.matches("^[a-z]+[a-z0-9+_.-]{1}+[a-z0-9]+@[a-z]+[.]+[a-z]+$", Email);
			if(emailresult==false) {
				System.out.println("\nEmail should contain only '@' followed by domain name and should be minimum of length 3 excluding the '@' and domain name and should contain only lowercase alphabets or digits before '@'.");
			}
		}while(emailresult==false);
		double amount=0;
		if(AccountType.toLowerCase().contains("savings")) {
			do {
				System.out.printf("\nEnter the initial amount you want to deposit in your savings account : ");
				amount = Double.parseDouble(br.readLine());
				if(amount<3000) {
					System.out.println("\nInitial amount for opening savings account cannot be less than Rs.3000.");
				}
			}while(amount<3000);
		}
		else {
			do {
				System.out.printf("\nEnter the initial amount you want to deposit in your current account : ");
				amount = Double.parseDouble(br.readLine());
				if(amount<10000) {
					System.out.println("\nInitial amount for opening current account cannot be less than Rs.10,000.");
				}
			}while(amount<10000);
		}
		accountNum=(int)(Math.random()*Math.pow(10,5));
		if(AccountType.toLowerCase().contains("savings")) {
			acc=new Account(accountNum,amount,"Savings");
		}else {
			acc=new Account(accountNum,amount,"Current");
		}
		long cardID = (int)(Math.random()*Math.pow(10,8));
		System.out.println("\nYour Account No. is "+accountNum+".");
		System.out.printf("Thank You for creating a "+acc.AccountType+" account with us.\nHere is your ATM Card of Card ID %d.\nSet a 3-digit PIN : ",cardID);
		int pin=100;
		do {
			pin=Integer.parseInt(br.readLine());
			if(pin>=100 && pin<=999) {
				break;
			}
			else {
				System.out.print("\nPin must be of 3 digits...Try Again...\nSet a 3-digit PIN : ");
			}
		}while(pin<100 || pin>999);
		Card=new ATMCard(pin,cardID,acc);
		System.out.println();

		ATM atm = new ATM(location[choice-1],BranchName[choice-1]);
		bc=new BankCustomer(CustomerName,Address,Email,Card,atm);
		System.out.println("Process Completed.");

		AccountList.add(bc);
	}

	// function to display the accounts in the order they are created
	public static void display() {
		if(AccountList.size()<1) {
			System.out.println("\nNo Accounts to display...");
		}else
		{
			for(int i=0;i<AccountList.size();i++) {
				System.out.print("\nAccount No."+(1+i));
				System.out.print("\n"+AccountList.get(i)+"\n");
			}
		}
	}

	// function to display available cash in cash dispenser
	public static void CashDispenserMessage() {
		System.out.printf("\nAvailable Cash in Cash Dispenser : Rs.%.2f\n",AvailableCash);
	}

	// function to deposit money in an account
	public static void deposit() {
		if(AccountList.size()<1) {
			System.out.println("\nMoney deposition is not possible without any account...");
		}
		else {
			Scanner sm=new Scanner(System.in);// Scanner object creation by Shrijit Majumder
			if(AccountList.size()==1) {
				BankCustomer bc1=AccountList.get(AccountList.size()-1);
				ATMCard ac1=bc1.Card;
				Account acc1=ac1.Acc;
				double amount;
				do {
					System.out.print("\nEnter the amount you want to deposit : ");
					amount=sm.nextDouble();
					if(amount<=0) {
						System.out.println("\nDeposit amount cannot be zero or negative...Try Again...");
					}
				}while(amount<=0);
				acc1.Balance=acc1.Balance+amount;
				acc1=new Account(acc1.IAccountNumber,acc1.Balance,acc1.AccountType);
				ac1=new ATMCard(ac1.PIN,ac1.CardID,ac1.Acc);
				bc1=new BankCustomer(bc1.CustomerName,bc1.Address,bc1.Email,ac1,bc1.atm);
				AvailableCash=AvailableCash+amount;
				AccountList.set(AccountList.size()-1,bc1);
				String decimal="%.2f";
				System.out.println("\nDeposit of Rs."+decimal.formatted(amount)+" in Account No. "+acc1.IAccountNumber+" is successful!!!");
				CashDispenserMessage();
			}
			else {
				System.out.print("\nEnter the account number in which you want to deposit money : ");
				int accountNumber=sm.nextInt();
				int temp=0;
				int k=0;
				for(int i=0;i<AccountList.size();i++) {
					BankCustomer bc1=AccountList.get(i);
					ATMCard ac1=bc1.Card;
					Account acc1=ac1.Acc;
					if(acc1.IAccountNumber==accountNumber) {
						k=i;
						temp=1;
						break;
					}
				}

				if(temp==0) {
					System.out.println("\nAccount Number "+accountNumber+" Not Found!!!");
				}
				else {
					BankCustomer bc1=AccountList.get(k);
					ATMCard ac1=bc1.Card;
					Account acc1=ac1.Acc;
					double amount;
					do {
						System.out.print("\nEnter the amount you want to deposit : ");
						amount=sm.nextDouble();
						if(amount<=0) {
							System.out.println("\nDeposit amount cannot be zero or negative...Try Again...");
						}
					}while(amount<=0);
					acc1.Balance=acc1.Balance+amount;
					acc1=new Account(acc1.IAccountNumber,acc1.Balance,acc1.AccountType);
					ac1=new ATMCard(ac1.PIN,ac1.CardID,ac1.Acc);
					bc1=new BankCustomer(bc1.CustomerName,bc1.Address,bc1.Email,ac1,bc1.atm);
					AccountList.set(k,bc1);
					String decimal="%.2f";
					System.out.println("\nDeposit of Rs."+decimal.formatted(amount)+" in Account No. "+acc1.IAccountNumber+" is successful!!!");
					CashDispenserMessage();
				}
			}
		}
	}

	// function to withdraw money from an account
	public static void withdraw() {
		Scanner sm=new Scanner(System.in);// Scanner object creation by Shrijit Majumder
		if(AccountList.size()==1) {
			BankCustomer bc1=AccountList.get(AccountList.size()-1);
			ATMCard ac1=bc1.Card;
			Account acc1=ac1.Acc;
			double amount;
			do {
				do {
					System.out.print("\nEnter the amount you want to withdraw : ");
					amount=sm.nextDouble();
					if(amount<=0) {
						System.out.println("\nWithdrawal amount cannot be zero or negative...Try Again...");
					}
				}while(amount<=0);

				if(acc1.Balance-amount<0) {
					System.out.println("Your Account does not have sufficient balance for such withdrawal...Withdrawal not possible!!! Try Again...");
				}
				else if(AvailableCash-amount<0) {
					System.out.println("Cash Dispenser does not have the required amount to be withdrawn...Try Again...");
				}
				else {
					int pin;
					do {
						System.out.print("\nEnter the pin for withdrawal : ");
						pin=sm.nextInt();
						if(pin==ac1.PIN) {
							acc1.Balance=acc1.Balance-amount;
							acc1=new Account(acc1.IAccountNumber,acc1.Balance,acc1.AccountType);
							ac1=new ATMCard(ac1.PIN,ac1.CardID,ac1.Acc);
							bc1=new BankCustomer(bc1.CustomerName,bc1.Address,bc1.Email,ac1,bc1.atm);
							AvailableCash=AvailableCash-amount;
							AccountList.set(AccountList.size()-1,bc1);
							String decimal="%.2f";
							System.out.println("\nWithdrawal of Rs."+decimal.formatted(amount)+" from Account No. "+acc1.IAccountNumber+" is successful!!!");
							CashDispenserMessage();
						}else {
							System.out.println("PIN not matching...Try Again...");
						}
					}while(pin!=ac1.PIN);
				}
			}while(acc1.Balance-amount<0 && AvailableCash-amount<0);
		}
		else {
			System.out.print("\nEnter the account number from where you want to withdraw money : ");
			int accountNumber=sm.nextInt();
			int temp=0;
			int k=0;
			for(int i=0;i<AccountList.size();i++) {
				BankCustomer bc1=AccountList.get(i);
				ATMCard ac1=bc1.Card;
				Account acc1=ac1.Acc;
				if(acc1.IAccountNumber==accountNumber) {
					k=i;
					temp=1;
					break;
				}
			}

			if(temp==0) {
				System.out.println("\nAccount Number "+accountNumber+" Not Found!!!");
			}
			else {
				BankCustomer bc1=AccountList.get(k);
				ATMCard ac1=bc1.Card;
				Account acc1=ac1.Acc;
				double amount;
				do {
					do {
						System.out.print("\nEnter the amount you want to withdraw : ");
						amount=sm.nextDouble();
						if(amount<=0) {
							System.out.println("\nWithdrawal amount cannot be zero or negative...Try Again...");
						}
					}while(amount<=0);

					if(acc1.Balance-amount<0) {
						System.out.println("Your Account does not have sufficient balance for such withdrawal...Withdrawal not possible!!! Try Again...");
					}
					else if(AvailableCash-amount<0) {
						System.out.println("Cash Dispenser does not have the required amount to be withdrawn...Try Again...");
					}
					else {
						int pin;
						do {
							System.out.print("\nEnter the pin for withdrawal : ");
							pin=sm.nextInt();
							if(pin==ac1.PIN) {
								acc1.Balance=acc1.Balance-amount;
								acc1=new Account(acc1.IAccountNumber,acc1.Balance,acc1.AccountType);
								ac1=new ATMCard(ac1.PIN,ac1.CardID,ac1.Acc);
								bc1=new BankCustomer(bc1.CustomerName,bc1.Address,bc1.Email,ac1,bc1.atm);
								AvailableCash=AvailableCash-amount;
								AccountList.set(k,bc1);
								String decimal="%.2f";
								System.out.println("\nWithdrawal of Rs."+decimal.formatted(amount)+" from Account No. "+acc1.IAccountNumber+" is successful!!!");
								CashDispenserMessage();
							}else {
								System.out.println("PIN not matching...Try Again...");
							}
						}while(pin!=ac1.PIN);
					}
				}while(acc1.Balance-amount<0 && AvailableCash-amount<0);
			}
		}
	}

	// function to transfer money from one account to another account
	public static void bank_transfer() {
		if(AccountList.size()<2) {
			System.out.println("\nMinimum two accounts are needed for bank transfer...The system has only one till now...");
		}
		else {
			Scanner sm=new Scanner(System.in);// Scanner object creation by Shrijit Majumder
			System.out.print("\nEnter the account number from where you want to transfer money : ");
			int accountNumber=sm.nextInt();
			int accountNumber1;
			int temp=0;
			int k=0;
			int temp1=0;
			int k1=0;
			for(int i=0;i<AccountList.size();i++) {
				BankCustomer bc1=AccountList.get(i);
				ATMCard ac1=bc1.Card;
				Account acc1=ac1.Acc;
				if(acc1.IAccountNumber==accountNumber) {
					k=i;
					temp=1;
					break;
				}
			}

			if(temp==0) {
				System.out.println("\nSource Account Not Found!!!");
			}
			else {
				System.out.print("\nEnter the account number in which you want to transfer money : ");
				accountNumber1=sm.nextInt();
				for(int i=0;i<AccountList.size();i++) {
					BankCustomer bc1=AccountList.get(i);
					ATMCard ac1=bc1.Card;
					Account acc1=ac1.Acc;
					if(acc1.IAccountNumber==accountNumber1) {
						k1=i;
						temp1=1;
						break;
					}
				}
				if(temp1==0) {
					System.out.println("\nDestination Account Not Found!!!");
				}
				else {
					BankCustomer bc1=AccountList.get(k);
					ATMCard ac1=bc1.Card;
					Account acc1=ac1.Acc;
					BankCustomer bc2=AccountList.get(k1);
					ATMCard ac2=bc2.Card;
					Account acc2=ac2.Acc;
					double amount;
					do {
						do {
							System.out.print("\nEnter the amount you want to transfer : ");
							amount=sm.nextDouble();
							if(amount<=0) {
								System.out.println("\nAmount to be transferred cannot be zero or negative...Try Again...");
							}
						}while(amount<=0);
						if(acc1.Balance-amount<0) {
							System.out.println("Your Account does not have sufficient balance for such withdrawal...Withdrawal not possible!!! Try Again...");
						}
						else if(AvailableCash-amount<0) {
							System.out.println("Cash Dispenser does not have the required amount to be withdrawn...Try Again...");
						}
						else {
							int pin;
							do {
								System.out.print("\nEnter the pin for withdrawal : ");
								pin=sm.nextInt();
								if(pin==ac1.PIN) {

									acc1.Balance=acc1.Balance-amount;
									acc1=new Account(acc1.IAccountNumber,acc1.Balance,acc1.AccountType);
									ac1=new ATMCard(ac1.PIN,ac1.CardID,ac1.Acc);
									bc1=new BankCustomer(bc1.CustomerName,bc1.Address,bc1.Email,ac1,bc1.atm);
									AccountList.set(k,bc1);
									acc2.Balance=acc2.Balance+amount;
									AvailableCash=AvailableCash-amount;
									acc2=new Account(acc2.IAccountNumber,acc2.Balance,acc2.AccountType);
									ac2=new ATMCard(ac2.PIN,ac2.CardID,ac2.Acc);
									bc2=new BankCustomer(bc2.CustomerName,bc2.Address,bc2.Email,ac2,bc2.atm);
									AccountList.set(k1,bc2);
									String decimal="%.2f";
									System.out.println("\nTransfer of Rs."+decimal.formatted(amount)+" from Account No. "+acc1.IAccountNumber+" to Account No. "+acc2.IAccountNumber+" is successful!!!");
									CashDispenserMessage();
								}else {
									System.out.println("PIN not matching...Try Again...");
								}
							}while(pin!=ac1.PIN);
						}
					}while(acc1.Balance-amount<0 && AvailableCash-amount<0);
				}
			}
		}
	}

	// function to start transaction activities
	public static void StartTransaction() {
		if(AccountList.size()<1) {
			System.out.println("\nTransaction is not possible without any account...");
		}
		else {
			Scanner  sm=new Scanner(System.in);// Scanner object creation by Shrijit Majumder
			CashDispenserMessage();
			char c='y';
			int choice;
			messageloop : while(c!='n' && c!='N' && c!='0') {
				System.out.print("\n1. Withdraw Money\n2. Bank Transfer\n3. Quit\nEnter your choice : ");
				choice = sm.nextInt();
				switch(choice) {
				case 1 : withdraw();
				break;
				case 2 : bank_transfer();
				break;
				case 3 : break messageloop;
				default : System.out.println("\nEnter correct choice 1-2");
				}
				System.out.println();
				System.out.print("\nDo you want to continue transaction?(y/n) ");
				c=sm.next().charAt(0);
				System.out.println();
			}
		}
	}

	// function to update PIN for the ATM card of an account
	public static void updatePIN() {
		Scanner sm=new Scanner(System.in);// Scanner object creation by Shrijit Majumder
		if(AccountList.size()==1) {
			BankCustomer bc1=AccountList.get(AccountList.size()-1);
			ATMCard ac1=bc1.Card;
			Account acc1=ac1.Acc;
			int pin;
			do {
				System.out.print("\nEnter the old pin one last time : ");
				pin=sm.nextInt();
				if(pin!=ac1.PIN) {
					System.out.println("PIN not matching...Try Again...");
				}
			}while(pin!=ac1.PIN);
			int newpin;
			System.out.print("\nEnter the new pin : ");
			do {
				newpin=sm.nextInt();
				if(newpin<100 || newpin>999) {
					System.out.print("\nPin must be of 3 digits...Try Again...\nSet a 3-digit PIN : ");
				}
				else if(newpin==pin) {
					System.out.print("\nNew pin and old pin cannot be same...Try Again...\nSet a 3-digit PIN : ");
				}

			}while((newpin<100 || newpin>999) || newpin==pin);
			ac1.PIN=newpin;
			acc1=new Account(acc1.IAccountNumber,acc1.Balance,acc1.AccountType);
			ac1=new ATMCard(ac1.PIN,ac1.CardID,ac1.Acc);
			bc1=new BankCustomer(bc1.CustomerName,bc1.Address,bc1.Email,ac1,bc1.atm);
			AccountList.set(AccountList.size()-1,bc1);
			System.out.println("PIN for Card ID "+ac1.CardID+" updated successfully...");
		}
		else {
			System.out.print("\nEnter the account number whose ATM PIN you want to update : ");
			int accountNumber=sm.nextInt();
			int temp=0;
			int k=0;
			for(int i=0;i<AccountList.size();i++) {
				BankCustomer bc1=AccountList.get(i);
				ATMCard ac1=bc1.Card;
				Account acc1=ac1.Acc;
				if(acc1.IAccountNumber==accountNumber) {
					k=i;
					temp=1;
					break;
				}
			}

			if(temp==0) {
				System.out.println("\nAccount Number "+accountNumber+" Not Found!!!");
			}
			else {
				BankCustomer bc1=AccountList.get(k);
				ATMCard ac1=bc1.Card;
				Account acc1=ac1.Acc;
				int pin;
				do {
					System.out.print("\nEnter the old pin one last time : ");
					pin=sm.nextInt();
					if(pin!=ac1.PIN) {
						System.out.println("PIN not matching...Try Again...");
					}
				}while(pin!=ac1.PIN);
				int newpin;
				System.out.print("\nEnter the new pin : ");
				do {
					newpin=sm.nextInt();
					if(newpin<100 || newpin>999) {
						System.out.print("\nPin must be of 3 digits...Try Again...\nSet a 3-digit PIN : ");
					}
					else if(newpin==pin) {
						System.out.print("\nNew pin and old pin cannot be same...Try Again...\nSet a 3-digit PIN : ");
					}

				}while((newpin<100 || newpin>999) || newpin==pin);
				ac1.PIN=newpin;
				acc1=new Account(acc1.IAccountNumber,acc1.Balance,acc1.AccountType);
				ac1=new ATMCard(ac1.PIN,ac1.CardID,ac1.Acc);
				bc1=new BankCustomer(bc1.CustomerName,bc1.Address,bc1.Email,ac1,bc1.atm);
				AccountList.set(k,bc1);
				System.out.println("PIN for Card ID "+ac1.CardID+" updated successfully...");
			}
		}
	}

	// function to update branch and location for an account
	public static void updateBranch_Location(){
		Scanner sm=new Scanner(System.in);// Scanner object creation by Shrijit Majumder
		if(AccountList.size()==1) {
			BankCustomer bc1=AccountList.get(AccountList.size()-1);
			ATMCard ac1=bc1.Card;
			Account acc1=ac1.Acc;
			ATM atm1=bc1.atm;
			int index=1;
			int m=0;
			String branch[]=new String[BranchName.length];
			String loc[]=new String[location.length];
			for(int i=0;i<location.length;i++) {
				if(!atm1.Location.equals(location[i])) {
					System.out.println((index++)+". "+BranchName[i]+", "+location[i]);
					branch[m]=BranchName[i];
					loc[m]=location[i];
					m++;
				}
			}
			int choice;
			do {
				System.out.print("\nAt which branch do you want to want to shift the account : ");
				choice=sm.nextInt();
				if(choice<=0 || choice>4) {
					System.out.println("Choice is invalid...Try Again...");
				}
			}while(choice>4 || choice<=0);

			atm1.Location=loc[choice-1];
			atm1.BranchName=branch[choice-1];
			acc1=new Account(acc1.IAccountNumber,acc1.Balance,acc1.AccountType);
			ac1=new ATMCard(ac1.PIN,ac1.CardID,ac1.Acc);
			bc1=new BankCustomer(bc1.CustomerName,bc1.Address,bc1.Email,ac1,atm1);
			AccountList.set(AccountList.size()-1,bc1);
			System.out.println("Location & Branch for Account No. "+acc1.IAccountNumber+" updated successfully...");
		}
		else {
			System.out.print("\nEnter the account number whose Branch & Location you want to update : ");
			int accountNumber=sm.nextInt();
			int temp=0;
			int k=0;
			for(int i=0;i<AccountList.size();i++) {
				BankCustomer bc1=AccountList.get(i);
				ATMCard ac1=bc1.Card;
				Account acc1=ac1.Acc;
				if(acc1.IAccountNumber==accountNumber) {
					k=i;
					temp=1;
					break;
				}
			}

			if(temp==0) {
				System.out.println("\nAccount Number "+accountNumber+" Not Found!!!");
			}
			else {
				BankCustomer bc1=AccountList.get(k);
				ATMCard ac1=bc1.Card;
				Account acc1=ac1.Acc;
				ATM atm1=bc1.atm;
				int index=1;
				int m=0;
				String branch[]=new String[BranchName.length];
				String loc[]=new String[location.length];
				for(int i=0;i<location.length;i++) {
					if(!atm1.Location.equals(location[i])) {
						System.out.println((index++)+". "+BranchName[i]+", "+location[i]);
						branch[m]=BranchName[i];
						loc[m]=location[i];
						m++;
					}
				}
				int choice;
				do {
					System.out.print("\nAt which branch do you want to want to shift the account : ");
					choice=sm.nextInt();
					if(choice<=0 || choice>4) {
						System.out.println("Choice is invalid...Try Again...");
					}
				}while(choice>4 || choice<=0);

				atm1.Location=loc[choice-1];
				atm1.BranchName=branch[choice-1];
				acc1=new Account(acc1.IAccountNumber,acc1.Balance,acc1.AccountType);
				ac1=new ATMCard(ac1.PIN,ac1.CardID,ac1.Acc);
				bc1=new BankCustomer(bc1.CustomerName,bc1.Address,bc1.Email,ac1,atm1);
				AccountList.set(k,bc1);
				System.out.println("Location & Branch for Account No. "+acc1.IAccountNumber+" updated successfully...");
			}
		}
	}

	// function to calculate interest as per date input
	public static void calculateInterest() throws IOException {
		if(AccountList.size()<1) {
			System.out.println("\nNo accounts available to compute interest...");
		}
		else {
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));// BufferedReader object creation by Shrijit Majumder
			String dateOfComputing;
			boolean dateresult=true;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			sdf.setLenient(false);
			Date dt=new Date();
			Date todate = new Date();
			do {
				try {
					System.out.print("\nEnter the date for computing the interest : ");
					dateOfComputing=br.readLine();
					dt=sdf.parse(dateOfComputing);
					dateresult=true;
					ZoneId zd = ZoneId.systemDefault();
					Instant ins1=todate.toInstant();
					Instant ins2=dt.toInstant();
					LocalDate ld1=ins1.atZone(zd).toLocalDate();
					LocalDate ld2=ins2.atZone(zd).toLocalDate();
					Period p=Period.between(ld1, ld2);
					if(p.isNegative()) {
						System.out.println("Date has gone in the past...Try Again...");
						dateresult=false;
					}
				}catch(ParseException e) {
					System.out.println("Either date format is wrong or date does not exist.");
					dateresult=false;
				}
			}while(dateresult==false);
			double interest=0;
			final float rateOfInterestForSavingsAccount=0.04f;
			for(int i=0;i<AccountList.size();i++) {
				BankCustomer bc1=AccountList.get(i);
				ATMCard ac1=bc1.Card;
				Account acc1=ac1.Acc;
				ATM atm=bc1.atm;
				ZoneId zd = ZoneId.systemDefault();
				Instant ins1=todate.toInstant();
				Instant ins2=dt.toInstant();
				LocalDate ld1=ins1.atZone(zd).toLocalDate();
				LocalDate ld2=ins2.atZone(zd).toLocalDate();
				Period p=Period.between(ld2, ld1);
				int total_days;
				if((dt.getYear()%4==0 && dt.getYear()%100!=0) || dt.getYear()%400==0) {
					total_days=366;
				}
				else {
					total_days=365;
				}
				if(acc1.AccountType.equalsIgnoreCase("Savings")) {
					interest=rateOfInterestForSavingsAccount*Math.abs(p.getYears()+(float)p.getMonths()/12+(float)p.getDays()/total_days)*acc1.Balance;
				}
				else {
					interest=0;
				}
				System.out.println("\nAccount No."+(i+1));
				if(acc1.AccountType.equals("Savings")) {
					System.out.println("Customer Name : "+bc1.CustomerName);
					System.out.println("Customer Address : "+bc1.Address);
					System.out.println("Customer Email : "+bc1.Email);
				}else {
					System.out.println("Company Name : "+bc1.CustomerName);
					System.out.println("Company Address : "+bc1.Address);
					System.out.println("Company Email : "+bc1.Email);
				}
				System.out.println("Date of computing interest : "+sdf.format(dt));
				System.out.println("Account Number : "+acc1.IAccountNumber);
				String sm_decimal_format="%.2f";
				if(acc1.AccountType.equals("Savings")) {
					System.out.println("Interest : Rs."+sm_decimal_format.formatted(interest));
				}
				else
				{
					System.out.println("Interest : N/A");
				}
				if(Math.abs(p.getYears()+(float)p.getMonths()/12+(float)p.getDays()/total_days)>=1) {
					if(Math.abs(p.getYears()+(float)p.getMonths()/12+(float)p.getDays()/total_days)==(int)Math.abs(p.getYears()+(float)p.getMonths()/12+(float)p.getDays()/total_days)) {
						if(Math.abs(p.getYears())==1) {
							System.out.println("Amount after "+Math.abs(p.getYears())+" year : Rs."+sm_decimal_format.formatted(acc1.Balance+interest));
						}
						else {
							System.out.println("Amount after "+Math.abs(p.getYears())+" years : Rs."+sm_decimal_format.formatted(acc1.Balance+interest));
						}
					}
					else {
						System.out.println("Amount after "+sm_decimal_format.formatted(Math.abs(p.getYears()+(float)p.getMonths()/12+(float)p.getDays()/total_days))+" years : Rs."+sm_decimal_format.formatted(acc1.Balance+interest));
					}
				}
				else
				{
					int numofdays;
					if(dt.getMonth()==0 || dt.getMonth()==2 || dt.getMonth()==4 || dt.getMonth()==6 || dt.getMonth()==7 || dt.getMonth()==9 || dt.getMonth()==11) {
						numofdays=31;
					}
					else if(dt.getMonth()==1) {
						numofdays=((dt.getYear()%4==0 && dt.getYear()%100!=0) || dt.getYear()%400==0)?29:28;
					}
					else {
						numofdays=30;
					}
					if(Math.abs(p.getMonths()+(float)p.getDays()/numofdays)>=1){
						if(Math.abs(p.getMonths()+(float)p.getDays()/numofdays)==(int)Math.abs(p.getMonths()+(float)p.getDays()/numofdays)) {
							if(Math.abs(p.getMonths())==1) {
								System.out.println("Amount after "+Math.abs(p.getMonths())+" month : Rs."+sm_decimal_format.formatted(acc1.Balance+interest));
							}
							else {
								System.out.println("Amount after "+Math.abs(p.getMonths())+" months : Rs."+sm_decimal_format.formatted(acc1.Balance+interest));
							}
						}
						else {
							System.out.println("Amount after "+sm_decimal_format.formatted(Math.abs(p.getMonths()+(float)p.getDays()/numofdays))+" months : Rs."+sm_decimal_format.formatted(acc1.Balance+interest));
						}
					}
					else
					{
						if(Math.abs(p.getDays())==(int)Math.abs(p.getDays())) {
							if(Math.abs(p.getDays())==1) {
								System.out.println("Amount after "+Math.abs(p.getDays())+" day : Rs."+sm_decimal_format.formatted(acc1.Balance+interest));
							}
							else {
								System.out.println("Amount after "+Math.abs(p.getDays())+" days : Rs."+sm_decimal_format.formatted(acc1.Balance+interest));
							}
						}
						else {
							System.out.println("Amount after "+sm_decimal_format.formatted(Math.abs(p.getDays()))+" days : Rs."+sm_decimal_format.formatted(acc1.Balance+interest));
						}
					}
				}
				System.out.println("Account Type : "+acc1.AccountType);
				System.out.println("Card Number : "+ac1.CardID);
				System.out.println("Branch Name : "+atm.BranchName);
				System.out.println("Location : "+atm.Location);
				System.out.println();
			}
		}
	}

	// function to instantiate account update activities
	public static void updateAccount() {
		if(AccountList.size()<1) {
			System.out.println("\nUpdating is not possible without any account...");
		}
		else {
			Scanner  sm=new Scanner(System.in);// Scanner object creation by Shrijit Majumder
			int choice;
			char c='y';
			messageloop : while(c!='n' && c!='N' && c!='0') {
				System.out.print("\n1. Update Pin\n2. Update Branch & Location\n3. Quit\nEnter your choice : ");
				choice = sm.nextInt();
				switch(choice) {
				case 1 : updatePIN();
				break;
				case 2 : updateBranch_Location();
				break;
				case 3 : break messageloop;
				default : System.out.println("\nEnter correct choice 1-2");
				}
				System.out.println();
				System.out.print("\nDo you want to continue updating?(y/n) ");
				c=sm.next().charAt(0);
				System.out.println();
			}
		}
	}

	// function to close an account
	public static void closeAccount() {
		if(AccountList.size()<1) {
			System.out.println("\nThere are no running accounts for closure.");
		}
		else if(AccountList.size()==1) {
			BankCustomer bc1=AccountList.get(AccountList.size()-1);
			ATMCard ac1=bc1.Card;
			Account acc1=ac1.Acc;
			AvailableCash=AvailableCash-acc1.Balance;
			System.out.println("\nAccount has been closed.");
			AccountList.remove(AccountList.size()-1);
		}
		else {
			Scanner  sm=new Scanner(System.in);// Scanner object creation by Shrijit Majumder
			int choice;
			char c='y';
			messageloop : while(c!='n' && c!='N' && c!='0') {
				System.out.print("\n1. Close Account By Account Number\n2. Close all the accounts\n3. Quit\nEnter your choice : ");
				choice = sm.nextInt();
				switch(choice) {
				case 1 : closeAccountByAccNo();
				break;
				case 2 : closeAllAccounts();
				break;
				case 3 : break messageloop;
				default : System.out.println("\nEnter correct choice 1-2");
				}
				System.out.println();
				System.out.print("\nDo you want to continue the process of account closure?(y/n) ");
				c=sm.next().charAt(0);
				System.out.println();
			}
		}
	}

	// function to close an account by account number
	public static void closeAccountByAccNo() {
		Scanner sm=new Scanner(System.in);// Scanner object creation by Shrijit Majumder
		System.out.print("\nEnter the account number of the account you want to close : ");
		int accountNumber=sm.nextInt();
		int temp=0;
		int k=0;
		for(int i=0;i<AccountList.size();i++) {
			BankCustomer bc1=AccountList.get(i);
			ATMCard ac1=bc1.Card;
			Account acc1=ac1.Acc;
			if(acc1.IAccountNumber==accountNumber) {
				k=i;
				temp=1;
				break;
			}
		}

		if(temp==0) {
			System.out.println("\nAccount Number "+accountNumber+" Not Found!!!");
		}
		else {
			BankCustomer bc1=AccountList.get(k);
			ATMCard ac1=bc1.Card;
			Account acc1=ac1.Acc;
			AvailableCash=AvailableCash-acc1.Balance;
			System.out.println("\nAccount with Account No. "+acc1.IAccountNumber+" has been closed.");
			AccountList.remove(k);
		}
	}

	// function to close all the accounts at the same time
	public static void closeAllAccounts() {
		for(int i=0;i<AccountList.size();i++) {
			BankCustomer bc1=AccountList.get(i);
			ATMCard ac1=bc1.Card;
			Account acc1=ac1.Acc;
			AvailableCash=AvailableCash-acc1.Balance;
		}
		AccountList.clear();
		System.out.println("\nAll the accounts has been closed.");
	}

	// main method for program execution
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner sm=new Scanner(System.in);// Scanner object creation by Shrijit Majumder
		int choice;
		char c='y';
		System.out.println("**********Welcome To SBI**********\n");
		messageloop : while(c!='n' && c!='N' && c!='0') {
			System.out.print("1. Create Account\n2. Display Account\n3. Deposit Money in Account\n4. Start Transaction in ATM\n5. Update Account\n6. Calculate interest for each account\n7. Close Account\n8. Check Available Cash in Cash Dispenser\n9. Exit\nEnter your choice : ");
			choice = sm.nextInt();
			switch(choice) {
			case 1 : CreateAccount();
			break;
			case 2 : display();
			break;
			case 3 : deposit();
			break;
			case 4 : StartTransaction();
			break;
			case 5 : updateAccount();
			break;
			case 6 : calculateInterest();
			break;
			case 7 : closeAccount();
			break;
			case 8 : CashDispenserMessage();
			break;
			case 9 : break messageloop;
			default : System.out.println("\nEnter correct choice 1-9...");
			}
			System.out.println();
			System.out.print("\nDo you want to continue bank operations?(y/n) ");
			c=sm.next().charAt(0);
			System.out.println();
		}
		System.out.println("\n----------Thank You----------");
		sm.close();
	}
}