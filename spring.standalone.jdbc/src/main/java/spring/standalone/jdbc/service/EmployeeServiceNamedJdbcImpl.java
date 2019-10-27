package spring.standalone.jdbc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import spring.standalone.jdbc.model.Employee;
import spring.standalone.jdbc.repo.EmployeeRepositoryNamedJdbc;

public class EmployeeServiceNamedJdbcImpl implements EmployeeServiceNamedJdbc{

	@Autowired
	EmployeeRepositoryNamedJdbc employeeRepositoryNamedJdbc;
	
	
	@Override
	public void saveEmployee(Employee e) {
		employeeRepositoryNamedJdbc.saveEmployee(e);
	}


	@Override
	public void saveEmployeesUsingBatchUpdate(List<Employee> employees) {
		employeeRepositoryNamedJdbc.saveEmployeesUsingBatchUpdate(employees);
	}


	@Override
	public List<Employee> findAll() {
		return employeeRepositoryNamedJdbc.findAll();
	}


	@Override
	public List<Employee> findAllByName(String name) {
		return employeeRepositoryNamedJdbc.findAllByName(name);
	}


	@Override
	public int getTotalNumberOfEmployees() {
		return employeeRepositoryNamedJdbc.getTotalNumberOfEmployees();
	}


	@Override
	public int updateEmployee() {
		return employeeRepositoryNamedJdbc.updateEmployee();
	}


	@Override
	public int deleteEmployee() {
		return employeeRepositoryNamedJdbc.deleteEmployee();
	}


	@Override
	public List<Integer> findAllByNameWithqueryForList() {
		return employeeRepositoryNamedJdbc.findAllByNameWithqueryForList();
	}

}
