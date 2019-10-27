package spring.javaconfig.hibernate.bankapp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.javaconfig.hibernate.bankapp.domain.BankAccountDetails;

@Repository
@Transactional
public class BankAccountDaoImpl implements BankAccountDao {
	
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public int createBankAccount(final BankAccountDetails bankAccountDetails) {
		sessionFactory.getCurrentSession().save(bankAccountDetails);
		return bankAccountDetails.getAccountId();
	}

	public List<BankAccountDetails> findAccountsWithBalanceGreaterThan(int balanceAmount) {
		return sessionFactory.getCurrentSession().createQuery(
				"SELECT details FROM BankAccountDetails details WHERE details.balanceAmount > :balanceAmount",
				BankAccountDetails.class).setParameter("balanceAmount", balanceAmount).getResultList();
	}

	@Override
	public void subtractFromAccount(int bankAccountId, int amount) {
		Session session = sessionFactory.getCurrentSession();
		BankAccountDetails bankAccountDetails = session.find(BankAccountDetails.class, bankAccountId);

		if (bankAccountDetails.getBalanceAmount() < amount) {
			throw new RuntimeException("Insufficient balance amount in bank account");
		}
		
		bankAccountDetails.setBalanceAmount(bankAccountDetails.getBalanceAmount() - amount);
		session.merge(bankAccountDetails);
	}
}
