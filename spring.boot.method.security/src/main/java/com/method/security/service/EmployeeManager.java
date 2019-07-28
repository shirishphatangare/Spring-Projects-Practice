package com.method.security.service;

import java.util.List;
import java.util.Optional;

import com.method.security.entity.Employee;

public interface EmployeeManager {
	public void addEmployee(Employee employee);
    public List<Employee> getAllEmployees();
    public void deleteEmployee(Integer employeeId);
    public Optional<Employee> findEmployee(Integer employeeId);
}
