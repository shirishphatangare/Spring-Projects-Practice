package spring.javaconfig.hibernate.bankapp.service;

import java.util.List;

import spring.javaconfig.hibernate.bankapp.domain.BankAccountDetails;

public interface BankAccountService {
	int createBankAccount(BankAccountDetails bankAccountDetails);

	List<BankAccountDetails> findAccountsWithBalanceGreaterThan(int balanceAmount);
}
