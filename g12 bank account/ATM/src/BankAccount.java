import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class BankAccount {
	
	int acctNum;
	double balance;
	String fName;
	String lName;
	String pswd;
	String log="";

	
	public BankAccount(int acctNum2, double bal, String fName2, String lName2, String pswd2, String log2) {
		acctNum = acctNum2;
		balance = bal;
		fName = fName2;
		lName = lName2;
		pswd = pswd2;
		log = log2;
	}


	public double myRound(double amt) {
		
		double rounded = Math.round(amt * 100.0) / 100.0; // => 1.23
		return rounded;
		
	}
	
	public boolean deposit(double amt) {
		
		if (amt == 0) {
			return false;
		}
		
		if (amt>0) {
			String time = genTimeStamp() + "     Deposit Successful ["+myRound(amt)+"]\n";
			log += time;
			balance+=amt;
			return true;
			
		} else if (amt<0){
			String time = genTimeStamp() + "     Deposit Failed ["+myRound(amt)+"]\n";
			log += time;
			return false;
		}
		return false;
	}

	public boolean withdraw(double amt) {
		
		if (amt == 0) {
			return false;
		}
		
		if (balance>=amt && amt >=0) {
			String time = genTimeStamp() + "     Withdrawl Successful ["+myRound(amt)+"]\n";
			log += time;
			balance-=amt;
			return true;
		} else {
			String time = genTimeStamp() + "     Withdrawl Failed ["+myRound(amt)+"]\n";
			log += time;
			return false;
		}
	}

	public boolean checkPswd(String toBeChecked) {
		if (pswd.equals(toBeChecked)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean resetPswd(String currentPswd, String newPswd) {
		if (checkPswd(currentPswd)) {
			String time = genTimeStamp() + "     Password Successfully Changed!\n";
			log += time;
			pswd=newPswd;
			return true;
		} else {
			String time = genTimeStamp() + "     Reset Password Failed!\n";
			log += time;
			return false;
		}
	}

	public boolean transferTo (double amt, OnlineBankAccount target) {
		if (amt<=0) {
			String failed = genTimeStamp()+"     Transfer Failed [$"+myRound(amt)+" to account "+target.acctNum+"]\n";
			log +=failed;
			return false;
		}
		if (balance >= amt ) {
			balance-= amt;
			target.balance += amt;

			String timeForSubject = genTimeStamp()+"     Transfer [$"+myRound(amt)+" sent to account "+target.acctNum+"]\n";
			log += timeForSubject;

			String timeForTarget = genTimeStamp()+"     Transfer [$"+myRound(amt)+" recieved from account "+acctNum+"]\n";
			target.log += timeForTarget;

			return true;
		} else {
			String failed = genTimeStamp()+"     Transfer Failed [$"+myRound(amt)+" to account "+target.acctNum+"]\n";
			log +=failed;
			return false;
		}
	}

	public static String genPswd(int MAX_PASSWORD_LENGTH) {
		String[] strArray = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
		String password="";

		Random randomGen = new Random();

		for (int i=0; i<MAX_PASSWORD_LENGTH; i++) {
			int rValue = randomGen.nextInt(strArray.length);
			password+=strArray[rValue];
		}

		return password;

	}


	public static int genAcctNum(int len) {

		String accountNum = "";
		Random randomGen = new Random();

		for (int i = 0; i<len; i++) {
			int digit = randomGen.nextInt(10);
			accountNum += String.valueOf(digit);
		}

		char zero = '0';
		if (accountNum.charAt(0) == zero) {
			return genAcctNum(len);
		} else {
			int account = Integer.parseInt(accountNum);
			return account;
		}
	}



	
	public String genTimeStamp() {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		return timeStamp;
	}

	public void setFName(String firstName) {
		fName = firstName;
	}

	public void setLName(String lastName) {
		lName = lastName;
	}

	public void resetAcctNum(int len) {
		int newAcctNum = genAcctNum(len);
		acctNum = newAcctNum;
	}

	public int getAcctNum() {
		return acctNum;
	}

	public double getBalance() {
		return balance;
	}

	public String getFName() {
		return fName;
	}

	public String getLName() {
		return lName;
	}

	public String getLog() {
		return log;
	}


	public void display() {

		System.out.println("Account Number: "+acctNum);
		System.out.println("Balance: "+balance);
		System.out.println("First Name: "+fName);
		System.out.println("Last Name: "+lName);
		System.out.println("Password: "+pswd);
		System.out.println("Log:\n"+log);



	}

}
