package spring.standalone.jdbc.repo;

import java.util.List;

import spring.standalone.jdbc.model.Employee;


public interface EmployeeRepositoryNamedJdbc {
	public void saveEmployee(Employee e);
	public void saveEmployeesUsingBatchUpdate(List<Employee> employees);
	public List<Employee> findAll();
	public List<Employee> findAllByName(String name);
	public int getTotalNumberOfEmployees();
	public int updateEmployee();
	public int deleteEmployee();
	public List<Integer> findAllByNameWithqueryForList();
}
