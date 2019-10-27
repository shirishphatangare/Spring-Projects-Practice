package spring.standalone.jdbc.transaction.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import spring.standalone.jdbc.transaction.exception.InsufficientFundsException;
import spring.standalone.jdbc.transaction.model.Account;
import spring.standalone.jdbc.transaction.model.AccountRowMapper;

public class AccountRepositoryImpl implements AccountRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void withdrawAccount(Account fromAccount, int amount) throws InsufficientFundsException {
		int accountFunds = fromAccount.getFundsInAccount();
		if(accountFunds < amount) {
			throw new InsufficientFundsException("Insufficient funds to withdraw");
		}
		
		int newAccountFunds = accountFunds - amount;
		
		String sql = "Update Account set account_funds = ? where account_number = ?";
		if(jdbcTemplate.update(sql, newAccountFunds, fromAccount.getAccountNumber()) > 0) {
			System.out.println("Account " + fromAccount.getAccountNumber() + " of " +  fromAccount.getAccountHolderName() + " was withdrawn for amount " + amount);
			System.out.println("Total available funds For Account " + fromAccount.getAccountNumber() + " are $" +  newAccountFunds);
		}
	}

	public void depositAccount(Account toAccount, int amount) {
		int newAccountFunds = toAccount.getFundsInAccount() + amount;
		String sql = "Update Account set account_funds = ? where account_number = ?";
		if(jdbcTemplate.update(sql, newAccountFunds, toAccount.getAccountNumber()) > 0) {
			System.out.println("Account " + toAccount.getAccountNumber() + " of " +  toAccount.getAccountHolderName() + " was deposited for amount " + amount);
			System.out.println("Total available funds For Account " + toAccount.getAccountNumber() + " are $" +  newAccountFunds);
		}
		
	}

	public Account findAccountById(int accountId) {
		String sql = "Select * from Account where account_number=?";
		return jdbcTemplate.queryForObject(sql, new AccountRowMapper(), accountId);
	}
	
}
