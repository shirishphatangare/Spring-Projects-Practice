package spring.standalone.jdbc.transaction.model;

public class Account {

	int accountNumber;
	String accountHolderName;
	int fundsInAccount;
	
	public Account(int accountNumber, String accountHolderName) {
		super();
		this.accountNumber = accountNumber;
		this.accountHolderName = accountHolderName;
	}
	
	public Account() {
		
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public int getFundsInAccount() {
		return fundsInAccount;
	}

	public void setFundsInAccount(int fundsInAccount) {
		this.fundsInAccount = fundsInAccount;
	}

	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", accountHolderName=" + accountHolderName
				+ ", fundsInAccount=" + fundsInAccount + "]";
	}
	
	
	
}
