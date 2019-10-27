package spring.standalone.jdbc.transaction.service;

import spring.standalone.jdbc.transaction.exception.InsufficientFundsException;
import spring.standalone.jdbc.transaction.model.Account;

public interface AccountService {
	public void completeBankTransaction(Account fromAccount, Account toAccount, int amount) throws InsufficientFundsException;
}
