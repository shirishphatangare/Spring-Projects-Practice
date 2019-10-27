package spring.springdata.jpa.bankapp.repository;

interface BankAccountRepositoryCustom {
	void subtractFromAccount(int bankAccountId, int amount);
}
