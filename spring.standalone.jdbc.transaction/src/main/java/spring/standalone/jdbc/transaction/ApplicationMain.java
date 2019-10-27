package spring.standalone.jdbc.transaction;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import spring.standalone.jdbc.transaction.config.AppConfig;
import spring.standalone.jdbc.transaction.exception.InsufficientFundsException;
import spring.standalone.jdbc.transaction.model.Account;
import spring.standalone.jdbc.transaction.repo.AccountRepository;
import spring.standalone.jdbc.transaction.service.AccountService;

public class ApplicationMain {

	public static void main(String[] args) throws InsufficientFundsException{
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        
        AccountService accountService = ctx.getBean(AccountService.class);
        AccountRepository accountRepository = ctx.getBean(AccountRepository.class);
        
        int amount = 50;
        int fromAccountId = 123234;
        int toAccountId = 463658;
        
        Account fromAccount = accountRepository.findAccountById(fromAccountId);
        Account toAccount = accountRepository.findAccountById(toAccountId);

        System.out.println("Printing Database State Before Transaction..");
        System.out.println(accountRepository.findAccountById(fromAccount.getAccountNumber()));
        System.out.println(accountRepository.findAccountById(toAccount.getAccountNumber()));
        
        accountService.completeBankTransaction(fromAccount, toAccount, amount);
        
        System.out.println("Printing Database State After Transaction..");
        System.out.println(accountRepository.findAccountById(fromAccount.getAccountNumber()));
        System.out.println(accountRepository.findAccountById(toAccount.getAccountNumber()));
        
        
        ctx.close();
	}

}
