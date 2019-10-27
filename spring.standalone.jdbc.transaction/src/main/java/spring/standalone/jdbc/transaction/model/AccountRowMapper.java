package spring.standalone.jdbc.transaction.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AccountRowMapper implements RowMapper<Account> {

	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		Account account = new Account();
		account.setAccountNumber(rs.getInt("account_number"));
		account.setAccountHolderName(rs.getString("account_name"));
		account.setFundsInAccount(rs.getInt("account_funds"));
		return account;
	}

}
