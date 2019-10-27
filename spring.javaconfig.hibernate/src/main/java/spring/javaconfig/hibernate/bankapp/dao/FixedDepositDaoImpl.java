package spring.javaconfig.hibernate.bankapp.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.javaconfig.hibernate.bankapp.domain.FixedDepositDetails;

@Repository
@Transactional
public class FixedDepositDaoImpl implements FixedDepositDao {

	@Autowired
    private SessionFactory sessionFactory;
	
	public int createFixedDeposit(final FixedDepositDetails fixedDepositDetails) {
		sessionFactory.getCurrentSession().save(fixedDepositDetails);
		return fixedDepositDetails.getFixedDepositId();
	}

	public FixedDepositDetails getFixedDeposit(final int fixedDepositId) {
		
		Query<FixedDepositDetails> query  = sessionFactory.getCurrentSession().createQuery("SELECT details FROM FixedDepositDetails details WHERE details.fixedDepositId  = ?", FixedDepositDetails.class);
		query.setParameter(0, fixedDepositId);
		
		return query.getSingleResult();
	}
}
