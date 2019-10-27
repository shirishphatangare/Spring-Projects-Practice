package spring.springdata.jpa.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.springdata.jpa.bankapp.domain.BankAccountDetails;
import spring.springdata.jpa.bankapp.repository.BankAccountRepository;

@Service
public class BankAccountServiceImpl implements BankAccountService {

	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Override
	@Transactional // Why @Transactional annotation here?
	public int createBankAccount(BankAccountDetails bankAccountDetails) {
		return bankAccountRepository.save(bankAccountDetails).getAccountId();
	}
}
