package spring.javaconfig.jpa.bankapp.dao;

import java.util.List;

import spring.javaconfig.jpa.bankapp.domain.BankAccountDetails;

public interface BankAccountDao {
	int createBankAccount(BankAccountDetails bankAccountDetails);
	List<BankAccountDetails> findAccountsWithBalanceGreaterThan(int balanceAmount);
	void subtractFromAccount(int bankAccountId, int amount);
}
