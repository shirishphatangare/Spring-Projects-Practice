package spring.javaconfig.hibernate.bankapp.dao;

import java.util.List;

import spring.javaconfig.hibernate.bankapp.domain.BankAccountDetails;

public interface BankAccountDao {
	int createBankAccount(BankAccountDetails bankAccountDetails);
	List<BankAccountDetails> findAccountsWithBalanceGreaterThan(int balanceAmount);
	void subtractFromAccount(int bankAccountId, int amount);
}
