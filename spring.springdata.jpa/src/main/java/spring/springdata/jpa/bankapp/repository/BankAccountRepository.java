package spring.springdata.jpa.bankapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.springdata.jpa.bankapp.domain.BankAccountDetails;

//@Repository annotation NOT required here
public interface BankAccountRepository extends JpaRepository<BankAccountDetails, Integer>, BankAccountRepositoryCustom {
	
}
