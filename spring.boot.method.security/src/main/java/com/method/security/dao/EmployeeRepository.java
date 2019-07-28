package com.method.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.method.security.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> { // Difference between JpaRepository and CrudRepository

    //List<Employee> findByName(String name);
	//Optional<Employee> findById(Integer id);

}