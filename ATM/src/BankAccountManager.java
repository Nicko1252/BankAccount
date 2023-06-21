public class BankAccountManager {


	private final int MAX_ACCOUNT_NUM = 15;
	BankAccount accountArray[] = new BankAccount[MAX_ACCOUNT_NUM];
	int numAccts = 0;

	BankAccountManager(){

	}

	private int findFirstEmpty() {
		for(int i = 0; i<accountArray.length; i++) {
			if(accountArray[i] == null) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean isAcctInArray(int accountNum) {
		for (int i = 0; i<accountArray.length; i++) {
			if (accountArray[i] != null) {
				if (accountArray[i].acctNum == accountNum) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isAcctNumUnique(int account) {
		for (int i = 0; i<=accountArray.length; i++) {
			if (accountArray[i].acctNum == account) {
				return false;
			}
		}
		return true;
	}
	public boolean addAcct(BankAccount accountNum) {

		for (int i=0; i<accountArray.length; i++) {
			if (accountArray[i] == null) {
				numAccts++;
				accountArray[i] = accountNum;
				return true;
			}
		}
		return false;
	}

	public boolean rmAcct(int accountNum) {
		for (int i = 0; i<=accountArray.length; i++) {
			if (accountArray[i].acctNum == accountNum) {
				accountArray[i] = null;
				numAccts--;
				return true;
			}
		}
		return false;
	}
	public BankAccount getAcct(int accountNum) {
		for (int i = 0; i<accountArray.length; i++) {
			if (accountArray[i] != null) {
				if (accountArray[i].acctNum == accountNum) {
					return accountArray[i];
				}
			}
		}
		return null;

	}
	 void depositIntoAll(double amt) {

		for (int i = 0; i<accountArray.length; i++) {
			if (accountArray[i] != null) {
				accountArray[i].deposit(amt);
			} 
		}

	}

	 void withdrawFromAll(double amt) {
		for (int i = 0; i<accountArray.length; i++) {
			if (accountArray[i] != null) {
				accountArray[i].widthdraw(amt);
			} 
		}
	}

		 void clearAccts() {
		for (int i = 0; i<accountArray.length; i++) {
			accountArray[i] = null;
		}

		numAccts=0;
	}
		 
	 void display() {
		for (int i = 0; i<accountArray.length; i++) {
			if (accountArray[i] == null) {
				System.out.println("empty");

			} else {
				System.out.println(accountArray[i].acctNum);
			}
		}
	}
	 public static void main(String [] args) {
		 System.out.println("");
	 }
}
