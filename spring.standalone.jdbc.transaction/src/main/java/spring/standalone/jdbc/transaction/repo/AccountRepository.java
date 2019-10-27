package spring.standalone.jdbc.transaction.repo;

import spring.standalone.jdbc.transaction.exception.InsufficientFundsException;
import spring.standalone.jdbc.transaction.model.Account;

public interface AccountRepository {
	
	public Account findAccountById(int accountId);
	public void withdrawAccount(Account fromAccount, int amount) throws InsufficientFundsException;
	public void depositAccount(Account toAccount, int amount);

}
