package spring.springdata.jpa.bankapp;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import spring.springdata.jpa.bankapp.domain.BankAccountDetails;
import spring.springdata.jpa.bankapp.domain.FixedDepositDetails;
import spring.springdata.jpa.bankapp.service.BankAccountService;
import spring.springdata.jpa.bankapp.service.FixedDepositService;

public class BankApp {
	private static Logger logger = LogManager.getLogger(BankApp.class);

	public static void main(String args[]) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("spring.springdata");
		context.refresh();

		/*
		 * --Uncomment this part to use XML-based configuration instead of Java-based
		 * configuration --
		 * 
		 * ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
		 * "classpath:META-INF/spring/applicationContext.xml");
		 */
		BankAccountService bankAccountService = context.getBean(BankAccountService.class);
		BankAccountDetails bankAccountDetails = new BankAccountDetails();
		bankAccountDetails.setBalanceAmount(1000);
		bankAccountDetails.setLastTransactionTimestamp(new Date());

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

		FixedDepositDetails _fdd = new FixedDepositDetails();
		_fdd.setActive("Y");
		_fdd.setBankAccountId(bankAccountDetails);
		_fdd.setFdCreationDate(new Date());
		_fdd.setFdAmount(500);
		_fdd.setTenure(6);
		fixedDepositService.createFixedDeposit(_fdd);

		logger.info("Total number of fixed deposits : " + fixedDepositService.count());
		logger.info("Number of fixed deposits with 12 months tenure " + fixedDepositService.countByTenure(12));

		List<FixedDepositDetails> removedFds = fixedDepositService.removeByTenure(12);
		logger.info("Removed " + removedFds.size() + " fixed deposits with tenure as 12 months");

		logger.info("Number of fixed deposits with 12 months tenure " + fixedDepositService.countByTenure(12));

		logger.info("findByTenure : " + fixedDepositService.findByTenure(6));

		logger.info("findTop2ByTenure : " + fixedDepositService.findTop2ByTenure(6));

		logger.info(
				"findTop2ByOrderByFdCreationDateDesc : " + fixedDepositService.findTop2ByOrderByFdCreationDateDesc());

		logger.info("findByTenureAndFdAmount : " + fixedDepositService.findByTenureAndFdAmount(6, 500));

		logger.info("findByTenure(tenure, pageable) : " + fixedDepositService.findByTenure(6, PageRequest.of(1, 10)));

		logger.info("findByTenure(tenure, sort) : "
				+ fixedDepositService.findByTenure(6, new Sort(Sort.Direction.ASC, "fdCreationDate")));
		
		// Paginating through large result sets - If you want to paginate through a large result set, define Slice<T> or Page<T> as the return type of the query method.
		// Page<T> returning queries can be time consuming for large result Sets.
		// Slice<T> as the return type, contains the results returned by the query and a flag that indicates if more entities are available in the data store.
		// -- using Slice<T> for paginated access to entities
		logger.info("findByFdAmount(500, new PageRequest(0, 2)) : ");

		Slice<FixedDepositDetails> slice = fixedDepositService.findByFdAmount(500, PageRequest.of(0, 2));
		
		// With Slice<T> as the return type, Spring Data retrieves one more result than requested.
		if (slice.hasContent()) { // Any result returned by the query?
			logger.info("Slice has content");
			List<FixedDepositDetails> list = slice.getContent(); // Obtain the results returned by the query
			for (FixedDepositDetails details : list) {
				logger.info("Fixed Deposit ID --> " + details.getFixedDepositId());
			}
		}
		if (slice.hasNext()) { // An extra result is returned by the query?
			Pageable pageable = slice.nextPageable();
			slice = fixedDepositService.findByFdAmount(500, pageable);
		}

		// --We can also use Stream<T> as the return type of the query method for handling large result sets
		// If you are using Stream<T> return type, the query method is not blocked until the complete result set is read into memory.
		logger.info("findAllByTenure : ");
		TransactionTemplate txTemplate = context.getBean(TransactionTemplate.class);
		txTemplate.execute(new TransactionCallbackWithoutResult() {
			// Below code that consumes data from Stream is executed within a transaction. The transaction ensures that the Stream is not closed until data is consumed from the Stream.
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try (Stream<FixedDepositDetails> stream = fixedDepositService.findAllByTenure(6)) {
					logger.info("count from stream --> " + stream.filter(t -> t.getActive().equals("Y")).count());
				}
			}
		});

		// -- async query method execution
		CompletableFuture<List<FixedDepositDetails>> future = fixedDepositService.findAllByFdAmount(500);
		while (!future.isDone()) {
			logger.info("Waiting for findAllByFdAmount method to complete .....");
		}
		logger.info(future.get());

		// --custom query using @Query annotation
		logger.info("findByCustomQuery : " + fixedDepositService.findByCustomQuery(6, 1000, "Y"));

		// --close ApplicationContext
		context.close();
	}
}