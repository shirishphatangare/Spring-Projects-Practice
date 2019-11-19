package dao;

import model.Department;

public class DepartmentDao {
	
	public Department findById(int id) {
		System.out.println("Searching for department..");
		return new Department();
	}
	
	public void save(Department department) {
		System.out.println("Saving department..");
	}

}
