package spring.standalone.jdbc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import spring.standalone.jdbc.model.Employee;
import spring.standalone.jdbc.repo.EmployeeRepository;

// We are not using any of the component annotations here to create bean. Instead we are using @bean annotation in @Configuration class (AppConfigM2)
public class EmployeeServiceImplM2 implements EmployeeService{
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public void startEmployessApp() {
		employeeRepository.startEmployessApp();
	}

	@Override
	public Employee findById(int id) {
		return employeeRepository.findById(id);
	}
	
	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}
	
	@Override
	public Employee findByIdUsingMap(int id) {
		return employeeRepository.findByIdUsingMap(id);
	}

	@Override
	public Employee findByIdAndNameUsingMap(int id, String name) {
		return employeeRepository.findByIdAndNameUsingMap(id, name);
	}

	@Override
	public String findEmployeeNameById(int id) {
		return employeeRepository.findEmployeeNameById(id);
	}

	@Override
	public int findEmployeeCount() {
		return employeeRepository.findEmployeeCount();
	}

	@Override
	public int saveEmployee(Employee e) {
		return employeeRepository.saveEmployee(e);
	}

	@Override
	public int updateEmployee(Employee e) {
		return employeeRepository.updateEmployee(e);
	}

	@Override
	public int deleteEmployee(Employee e) {
		return employeeRepository.deleteEmployee(e);
	}

	@Override
	public Boolean saveEmployeeByPreparedStatement(Employee e) {
		return employeeRepository.saveEmployeeByPreparedStatement(e);
	}

	@Override
	public List<Employee> getAllEmployeesUsingResultSetExtractor() {
		return employeeRepository.getAllEmployeesUsingResultSetExtractor();
	}

	@Override
	public List<Employee> getAllEmployeesRowMapper() {
		return employeeRepository.getAllEmployeesRowMapper();
	}

	@Override
	public List<Employee> getAllEmployeesRowCallbackHandler() {
		return employeeRepository.getAllEmployeesRowCallbackHandler();
	}

	@Override
	public List<Employee> getEmployeeByIdRowCallbackHandler(int id) {
		return employeeRepository.getEmployeeByIdRowCallbackHandler(id);
	}

	@Override
	public void printAllNamesUsingSqlRowSet() {
		employeeRepository.printAllNamesUsingSqlRowSet();
		
	}

	@Override
	public void executeSP() {
		employeeRepository.executeSP();
	}

	@Override
	public int[] saveEmployeesWithBatchUpdate(List<Employee> employees) {
		return employeeRepository.saveEmployeesWithBatchUpdate(employees);
	}

}
