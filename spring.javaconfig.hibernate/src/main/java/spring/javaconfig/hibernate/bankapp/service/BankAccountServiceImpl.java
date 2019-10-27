package spring.javaconfig.hibernate.bankapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.javaconfig.hibernate.bankapp.dao.BankAccountDao;
import spring.javaconfig.hibernate.bankapp.domain.BankAccountDetails;

@Service
public class BankAccountServiceImpl implements BankAccountService {

	@Autowired
	private BankAccountDao bankAccountDao;

	public List<BankAccountDetails> findAccountsWithBalanceGreaterThan(int balanceAmount) {
		return bankAccountDao.findAccountsWithBalanceGreaterThan(balanceAmount);
	}

	@Override
	@Transactional
	public int createBankAccount(BankAccountDetails bankAccountDetails) {
		return bankAccountDao.createBankAccount(bankAccountDetails);
	}
}
