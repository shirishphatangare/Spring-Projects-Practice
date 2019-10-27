package spring.standalone.jdbc.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import spring.standalone.jdbc.transaction.exception.InsufficientFundsException;
import spring.standalone.jdbc.transaction.model.Account;
import spring.standalone.jdbc.transaction.repo.AccountRepository;

public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;
	
	// value attribute is a qualifier value for the specified transaction. - May be used to determine the target transaction manager, matching the qualifier value (or the bean name) of a specific PlatformTransactionManager bean definition.
	// timeout attribute is in seconds
	@Transactional(value="AccountServiceTransaction", isolation=Isolation.READ_UNCOMMITTED, propagation=Propagation.REQUIRED,readOnly = false, rollbackFor = Exception.class, timeout = 6)
	public void completeBankTransaction(Account fromAccount, Account toAccount, int amount) throws InsufficientFundsException{
		accountRepository.withdrawAccount(fromAccount, amount);
		accountRepository.depositAccount(toAccount, amount);
	}

	
}
