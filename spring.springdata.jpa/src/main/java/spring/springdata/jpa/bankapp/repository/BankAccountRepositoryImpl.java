package spring.springdata.jpa.bankapp.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import spring.springdata.jpa.bankapp.domain.BankAccountDetails;

// Adding custom method to a repository

// Notice that @Repository annotation is not used here. As the BankAccountRepositoryImpl class follows the <your-repository-interface>Impl naming convention, it is
// automatically picked-up by Spring Data.
public class BankAccountRepositoryImpl implements BankAccountRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager; // Query execution in JPA way when adding a custom method
	
	@Override
	public void subtractFromAccount(int bankAccountId, int amount) {
		BankAccountDetails bankAccountDetails = entityManager.find(BankAccountDetails.class, bankAccountId);
		if (bankAccountDetails.getBalanceAmount() < amount) {
			throw new RuntimeException("Insufficient balance amount in bank account");
		}
		bankAccountDetails.setBalanceAmount(bankAccountDetails.getBalanceAmount() - amount);
		entityManager.merge(bankAccountDetails);
	}
}
