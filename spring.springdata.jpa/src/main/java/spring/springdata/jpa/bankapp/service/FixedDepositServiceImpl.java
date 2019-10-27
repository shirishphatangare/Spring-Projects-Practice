package spring.springdata.jpa.bankapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.springdata.jpa.bankapp.domain.FixedDepositDetails;
import spring.springdata.jpa.bankapp.exceptions.NoFixedDepositFoundException;
import spring.springdata.jpa.bankapp.repository.BankAccountRepository;
import spring.springdata.jpa.bankapp.repository.FixedDepositRepository;

@Service
public class FixedDepositServiceImpl implements FixedDepositService {

	@Autowired
	private FixedDepositRepository fixedDepositRepository;

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Override
	@Transactional
	public int createFixedDeposit(FixedDepositDetails fdd) throws Exception {
		bankAccountRepository.subtractFromAccount(fdd.getBankAccountId().getAccountId(), fdd.getFdAmount());
		return fixedDepositRepository.save(fdd).getFixedDepositId();
	}

	@Override
	@Transactional
	public FixedDepositDetails getFixedDeposit(int fixedDepositId) {
		return fixedDepositRepository.findById(fixedDepositId);
	}

	public long count() {
		return fixedDepositRepository.count();
	}

	public long countByTenure(int tenure) {
		return fixedDepositRepository.countByTenure(tenure);
	}

	@Transactional
	public List<FixedDepositDetails> removeByTenure(int tenure) {
		return fixedDepositRepository.removeByTenure(tenure);
	}

	@Override
	public List<FixedDepositDetails> findByTenure(int tenure) {
		try {
			return fixedDepositRepository.findByTenure(tenure);
		} catch (NoFixedDepositFoundException e) {
			return new ArrayList<FixedDepositDetails>();
		}
	}

	@Override
	public List<FixedDepositDetails> findByTenureLessThan(int tenure) {
		return fixedDepositRepository.findByTenureLessThan(tenure);
	}

	@Override
	public List<FixedDepositDetails> findByFdAmountGreaterThan(int fdAmount) {
		return fixedDepositRepository.findByFdAmountGreaterThan(fdAmount);
	}

	@Override
	public List<FixedDepositDetails> findByCustomQuery(int tenure, int fdAmount, String active) {
		return fixedDepositRepository.findByCustomQuery(tenure, fdAmount, active);
	}

	public List<FixedDepositDetails> findByTenure(int tenure, Pageable pageable) {
		return fixedDepositRepository.findByTenure(tenure, pageable);
	}

	public List<FixedDepositDetails> findByTenure(int tenure, Sort sort) {
		return fixedDepositRepository.findByTenure(tenure, sort);
	}

	@Override
	public Slice<FixedDepositDetails> findByFdAmount(int amount, Pageable pageable) {
		return fixedDepositRepository.findByFdAmount(amount, pageable);
	}

	@Override
	public List<FixedDepositDetails> findTop2ByTenure(int tenure) {
		return fixedDepositRepository.findTop2ByTenure(tenure);
	}

	@Override
	public List<FixedDepositDetails> findTop2ByOrderByFdCreationDateDesc() {
		return fixedDepositRepository.findTop2ByOrderByFdCreationDateDesc();
	}

	@Override
	public List<FixedDepositDetails> findByTenureAndFdAmount(int tenure, int fdAmount) {
		return fixedDepositRepository.findByTenureAndFdAmount(tenure, fdAmount);
	}

	@Override
	@Transactional(readOnly = true)
	public Stream<FixedDepositDetails> findAllByTenure(int tenure) {
		return fixedDepositRepository.findAllByTenure(tenure);
	}

	@Override
	public CompletableFuture<List<FixedDepositDetails>> findAllByFdAmount(int fdAmount) {
		return fixedDepositRepository.findAllByFdAmount(fdAmount);
	}
	
}
