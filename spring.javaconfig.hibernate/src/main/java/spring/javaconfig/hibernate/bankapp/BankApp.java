package spring.javaconfig.hibernate.bankapp;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import spring.javaconfig.hibernate.bankapp.domain.BankAccountDetails;
import spring.javaconfig.hibernate.bankapp.domain.FixedDepositDetails;
import spring.javaconfig.hibernate.bankapp.service.BankAccountService;
import spring.javaconfig.hibernate.bankapp.service.FixedDepositService;

public class BankApp {
	private static Logger logger = LogManager.getLogger(BankApp.class);

	public static void main(String args[]) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("spring.javaconfig.hibernate.bankapp"); // Just "spring.javaconfig" also can be used here
		context.refresh();

		BankAccountService bankAccountService = context.getBean(BankAccountService.class);
		BankAccountDetails bankAccountDetails = new BankAccountDetails();
		bankAccountDetails.setBalanceAmount(1000);
		bankAccountDetails.setLastTransactionTimestamp(new Date());
		
		List<BankAccountDetails> results = bankAccountService.findAccountsWithBalanceGreaterThan(100);
		logger.info("Bank accounts with balance > 100 are: ");
		for(BankAccountDetails details : results) {
			logger.info(details.getAccountId());
		}

		int bankAccountId = bankAccountService.createBankAccount(bankAccountDetails);

		logger.info("Created bank account with id - " + bankAccountId);

		FixedDepositService fixedDepositService = context.getBean(FixedDepositService.class);

		FixedDepositDetails fdd = new FixedDepositDetails();
		fdd.setActive("Y");
		fdd.setBankAccountId(bankAccountDetails);
		fdd.setFdCreationDate(new Date());
		fdd.setFdAmount(500);
		fdd.setTenure(12);
		int fixedDepositId = fixedDepositService.createFixedDeposit(fdd);
		logger.info("Created fixed deposit with id - " + fixedDepositId);

		logger.info(fixedDepositService.getFixedDeposit(fixedDepositId));

		context.close();
	}
}
