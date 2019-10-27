package spring.javaconfig.hibernate.bankapp.service;

import spring.javaconfig.hibernate.bankapp.domain.FixedDepositDetails;


public interface FixedDepositService {
	int createFixedDeposit(FixedDepositDetails fdd) throws Exception;
	FixedDepositDetails getFixedDeposit(int fixedDepositId);
}
