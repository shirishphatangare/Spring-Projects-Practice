package spring.springdata.jpa.bankapp.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import spring.springdata.jpa.bankapp.domain.FixedDepositDetails;
import spring.springdata.jpa.bankapp.exceptions.NoFixedDepositFoundException;


/* If we want to override FixedDepositRepository’s findByTenure method such that it throws a
NoFixedDepositFoundException when no fixed deposits are found for the given tenure. To achieve this requirement,
simply define a FixedDepositRepositoryImpl class (notice that the naming convention is <your-repository-interface>Impl) 
that contains the custom implementation for the findByTenure method. */ 

// Notice no @Repository annotation here since we used proper naming convention
public class FixedDepositRepositoryImpl {

	@PersistenceContext
	private EntityManager entityManager;

	public List<FixedDepositDetails> findByTenure(int tenure) {
		List<FixedDepositDetails> fds = entityManager
				.createQuery("SELECT details from FixedDepositDetails details where details.tenure = :tenure",
						FixedDepositDetails.class)
				.setParameter("tenure", tenure).getResultList();
		if (fds.isEmpty()) {
			throw new NoFixedDepositFoundException("No fixed deposits found");
		}
		return fds;
	}
}
