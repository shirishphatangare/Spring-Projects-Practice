package spring.mvc.javaconfig.bankapp.service;

import java.util.List;

import spring.mvc.javaconfig.bankapp.domain.FixedDepositDetails;

public interface FixedDepositService {
	List<FixedDepositDetails> getFixedDeposits();

	void saveFixedDeposit(FixedDepositDetails fixedDepositDetails);

	void closeFixedDeposit(int fixedDepositId);

	FixedDepositDetails getFixedDeposit(int fixedDepositId);
	
	void editFixedDeposit(FixedDepositDetails fixedDepositDetails);
}
