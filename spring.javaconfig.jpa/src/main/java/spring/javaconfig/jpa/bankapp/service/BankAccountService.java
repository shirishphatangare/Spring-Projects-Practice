package spring.javaconfig.jpa.bankapp.service;

import java.util.List;

import spring.javaconfig.jpa.bankapp.domain.BankAccountDetails;

public interface BankAccountService {
	int createBankAccount(BankAccountDetails bankAccountDetails);

	List<BankAccountDetails> findAccountsWithBalanceGreaterThan(int balanceAmount);
}
