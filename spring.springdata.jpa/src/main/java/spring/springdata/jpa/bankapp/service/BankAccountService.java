package spring.springdata.jpa.bankapp.service;

import spring.springdata.jpa.bankapp.domain.BankAccountDetails;

public interface BankAccountService {
	int createBankAccount(BankAccountDetails bankAccountDetails);
}
