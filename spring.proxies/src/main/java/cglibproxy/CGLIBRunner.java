package cglibproxy;

import org.springframework.cglib.proxy.Enhancer;

import dao.DepartmentDao;
import model.Department;

public class CGLIBRunner {

	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setCallback(new DepartmentDaoMethodInterceptor());
		enhancer.setSuperclass(DepartmentDao.class);
		DepartmentDao departmentDao = (DepartmentDao) enhancer.create();
		
		Department department = departmentDao.findById(5);
		departmentDao.save(department);
		
	}

}
