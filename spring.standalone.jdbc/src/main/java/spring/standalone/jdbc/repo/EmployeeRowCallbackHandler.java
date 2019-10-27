package spring.standalone.jdbc.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;

import spring.standalone.jdbc.model.Employee;

public class EmployeeRowCallbackHandler implements RowCallbackHandler {
    private List<Employee> aList;
     
    public EmployeeRowCallbackHandler() {
        aList = new ArrayList<Employee>();
    }
    
    @Override
    public void processRow(ResultSet rs) throws SQLException {
        aList.add(extractEmployeeFromRs(rs));
    }
     
    public List<Employee> getEmployeeList() {
        return aList;
    } 
    
    
    public static Employee extractEmployeeFromRs(ResultSet rs) throws SQLException {
    	Employee employee = new Employee();
    	employee.setId(rs.getInt("ID"));
    	employee.setName(rs.getString("NAME"));
        return employee;
    }
    
}
