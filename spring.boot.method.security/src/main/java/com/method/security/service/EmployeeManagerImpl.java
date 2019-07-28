package com.method.security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.method.security.dao.EmployeeRepository;
import com.method.security.entity.Employee;

@Service
public class EmployeeManagerImpl implements EmployeeManager {
	
	@Autowired
    private EmployeeRepository employeeRepository;
	
	public void setEmployeeRepository(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	@Transactional
	public void addEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	@Override
	@Transactional
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteEmployee(Integer employeeId) {
		employeeRepository.deleteById(employeeId);
	}
	
	@Override
	@Transactional
	public Optional<Employee> findEmployee(Integer employeeId) {
		return employeeRepository.findById(employeeId);
	}

	
}
