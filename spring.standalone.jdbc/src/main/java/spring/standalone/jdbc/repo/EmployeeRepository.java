package spring.standalone.jdbc.repo;

import java.util.List;

import spring.standalone.jdbc.model.Employee;


public interface EmployeeRepository {
	public void startEmployessApp();
	public Employee findById(int id);
	public List<Employee> findAll();
	public Employee findByIdUsingMap(int id);
	public Employee findByIdAndNameUsingMap(int id, String name);
	public String findEmployeeNameById(int id);
	public int findEmployeeCount();
	public int saveEmployee(Employee e);
	public int updateEmployee(Employee e);
	public int deleteEmployee(Employee e);
	public Boolean saveEmployeeByPreparedStatement(Employee e);
	public List<Employee> getAllEmployeesUsingResultSetExtractor();
	public List<Employee> getAllEmployeesRowMapper();
	public List<Employee> getAllEmployeesRowCallbackHandler();
	public List<Employee> getEmployeeByIdRowCallbackHandler(int id);
	public void printAllNamesUsingSqlRowSet();
	public int[] saveEmployeesWithBatchUpdate(List<Employee> employees);
	public void executeSP();
}
